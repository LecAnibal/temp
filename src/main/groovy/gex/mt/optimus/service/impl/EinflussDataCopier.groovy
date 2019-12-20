package gex.mt.optimus.service.impl

import com.google.gson.Gson
import gex.mt.optimus.service.DataCopier
import gex.mt.optimus.service.OptimusSyncService
import gex.mt.ulama.messaging.UlamaAsyncServiceRequester
import groovy.transform.ToString
import groovy.util.logging.Slf4j
import org.apache.commons.lang.exception.ExceptionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.jdbc.core.ColumnMapRowMapper
import org.springframework.jdbc.core.RowCallbackHandler
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.util.StopWatch
import java.sql.ResultSet
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

/**
 * Created by angelpimentel on 8/21/15.
 */
@Slf4j
class EinflussDataCopier implements DataCopier {
  @Autowired
  @Qualifier('einflussJdbcTemplate')
  NamedParameterJdbcTemplate sourceTemplate

  @Autowired
  @Qualifier('statsJdbcTemplate')
  NamedParameterJdbcTemplate destinationTemplate

  @Autowired
  UlamaAsyncServiceRequester ulamaAsyncServiceRequester

  @Autowired
  OptimusSyncService syncService

  @Value('${optimus.migration.seconds:2}')
  Long delaySeconds

  @Value('${gex.ulama.migration.allowedErrorPercentage:0.0}')
  double allowedErrorPercentage

  @Autowired
  UlamaAsyncServiceRequester serviceRequester

  @Autowired
  Gson gson


  ScheduledExecutorService delayedExecutor = Executors.newScheduledThreadPool(1)

  String queryParameters
  String sourceQuery
  String destinationQuery
  String setupQuery
  String cleanupQuery
  String stat

  void copy(Map<String, ?> parameters) {
    CopyTaskStatus taskStatus = new CopyTaskStatus(taskParameters: parameters)
    try {
      verifyCopy(
        cleanup(
          executeCopy(
            setup(parameters, taskStatus))))
      executeMigrationVerifications((String) parameters.migrationFlag, (Long) parameters.migrationLastFlag)
    } catch (Exception e) {
      log.error('Unexpected exception for sync event', e)
      insertSyncMessageError(parameters.toString(), taskStatus.totalEinfluss, taskStatus.totalUlama, taskStatus.errorPercentage, ExceptionUtils.getRootCauseMessage(e), parameters.migrationFlag?.toString())
    }
  }


  CopyTaskStatus setup(Map<String, ?> parameters, CopyTaskStatus taskStatus) {
    if (setupQuery) {
      log.debug('Executing setup query: {}', setupQuery)
      destinationTemplate.update(setupQuery, parameters)
    }
    return taskStatus
  }

  CopyTaskStatus executeCopy(CopyTaskStatus taskStatus) {
    log.debug('Executing source query: {}', sourceQuery)
    Map<String, ?> parameters = taskStatus.taskParameters
    StopWatch stopWatch = new StopWatch()
    stopWatch.start()
    Map result = sourceTemplate.queryForMap("select Count(*) as totalEinfluss from (${sourceQuery}) as x", parameters)
    taskStatus.totalEinfluss = result.totalEinfluss
    sourceTemplate.query(sourceQuery, parameters, [
      processRow: { ResultSet rs ->
        ColumnMapRowMapper rowMapper = new ColumnMapRowMapper()
        Map rowMap = rowMapper.mapRow(rs, rs.getRow())
        try {
          destinationTemplate.update(destinationQuery, rowMap)
          taskStatus.totalUlama = taskStatus.totalUlama + 1
        } catch (Exception e) {
          log.warn("Could not insert values in table ${stat} -> ${rowMap}")
          taskStatus.errorMessage = e.message
        }
      }
    ] as RowCallbackHandler)
    stopWatch.stop()
    taskStatus.totalTime = stopWatch.totalTimeMillis
    return taskStatus
  }

  CopyTaskStatus verifyCopy(CopyTaskStatus taskStatus) {
    Map<String, ?> parameters = taskStatus.taskParameters
    double successPercentage = 100.0
    if(taskStatus.totalEinfluss) {
      successPercentage = ((double) taskStatus.totalUlama / (double) taskStatus.totalEinfluss) * 100.0
    }
    taskStatus.errorPercentage = 100.0 - successPercentage
    boolean success = taskStatus.errorPercentage <= allowedErrorPercentage
    if (success) {
      if (taskStatus.totalUlama) {
        log.info 'Copied {} rows for parameters {}  in {} ms.', taskStatus.totalUlama, parameters, taskStatus.totalTime
        if (!parameters.migrationFlag) {
          ulamaAsyncServiceRequester.sendRequest('DataViewService', 'refreshViews', [table: stat])
        }
      } else {
        log.warn "Could not find einfluss results for parameters: ${parameters}"
      }
    } else {
      if (taskStatus.totalUlama) {
        log.warn 'Only {} of {} rows could be copied for parameters: {}', taskStatus.totalUlama, taskStatus.totalEinfluss,  parameters
      } else {
        log.warn "Could not find einfluss results for parameters: ${parameters}"
      }
      throw new IllegalStateException(taskStatus.errorMessage ?: "Unexpected state a after copy task -> ${taskStatus}")
    }
    return taskStatus
  }

  CopyTaskStatus cleanup(CopyTaskStatus status) {
    if (cleanupQuery) {
      log.debug('Executing cleanup query: {}', cleanupQuery)
      destinationTemplate.update(cleanupQuery, status.taskParameters)
    }
    status
  }

  void executeMigrationVerifications(String migrationId, Long migrationStartTime) {
    if (migrationId && migrationStartTime) {
      delayedExecutor.schedule(new MigrationStatusReportTask(startTime: migrationStartTime, migrationId: migrationId), delaySeconds, TimeUnit.SECONDS)
    }
  }

  void insertSyncMessageError(String message, Long totalEinfluss, Long totalUlama, Double error_percentage, String errorMessage, String migrationId = null) {
    Map args = [
      migrationId     : migrationId,
      table           : stat,
      status          : 'error',
      errorPercentage : error_percentage,
      totalEinfluss   : totalEinfluss,
      totalUlama      : totalUlama,
      syncEvent       : message,
      lastErrorMessage: errorMessage,
      syncEventDate   : new Date()
    ]

    destinationTemplate.update('''INSERT INTO optimus_events_error_log (migration_id, table_name, status, error_percentage, total_einfluss, total_ulama, last_error_message, sync_event, sync_event_date)
       VALUES (:migrationId, :table, :status, :errorPercentage, :totalEinfluss, :totalUlama, :lastErrorMessage, :syncEvent, :syncEventDate)''', args)

    if (!migrationId) {
      try {
        serviceRequester.sendRequest('EmailService', 'sendEmail', [
          subject: "Ulama OptimusLiveMessage Alarm - ${error_percentage}% of requests failed.",
          body   : "Error : message ${message} \n Exception: ${errorMessage}"
        ])
      } catch (Exception e) {
        log.warn "coulnd not send alarm"
      }

    }
  }

  class MigrationStatusReportTask implements Runnable {

    Long startTime

    String migrationId

    @Override
    void run() {
      try {
        syncService.createMigrationStatusReport(migrationId, startTime)
      } catch (Exception e) {
        log.error("Exception", e.message)
      }
    }
  }


  @ToString
  class CopyTaskStatus {
    Map<String, ?> taskParameters = null
    Long totalEinfluss = 0L
    Long totalUlama = 0L
    Double errorPercentage = 100.0
    String errorMessage = null
    Long totalTime = 0L
  }
}
