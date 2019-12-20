package gex.mt.optimus.service

import gex.mt.optimus.config.AmqpConfiguration
import gex.mt.optimus.config.CopiersConfiguration
import gex.mt.ulama.messaging.UlamaAsyncServiceRequester
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.lang.annotation.Annotation
import java.lang.reflect.Method

/**
 * Created by LecAnibal on 10/02/16.
 */
@Slf4j
@Component
class OptimusSyncService {

  @Autowired
  RabbitTemplate rabbitTemplate

  @Autowired
  @Qualifier('einflussJdbcTemplate')
  NamedParameterJdbcTemplate sourceTemplate

  @Autowired
  @Qualifier('statsJdbcTemplate')
  NamedParameterJdbcTemplate destinationTemplate

  @Value('${gex.optimus.migration.allowedErrorPercentage:0.0}')
  double allowedErrorPercentage

  @Autowired
  DataTransformer dataTransformer

  @Autowired
  UlamaAsyncServiceRequester serviceRequester


  List<String> getOptimusTables() {
    final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(CopiersConfiguration.getDeclaredMethods()));
    List tables = []

    allMethods.each { method ->
      if (method.isAnnotationPresent(ConfigurationProperties)) {
        Annotation annotInstance = method.getAnnotation(ConfigurationProperties);
        tables.add(annotInstance.prefix().toString())
      }
    }
    Collections.sort(tables)
    tables
  }

  void syncAllTables() {
    publishFullTableSyncEvents(getOptimusTables())
  }

  void syncTables(List<String> tables) {
    publishFullTableSyncEvents(tables)
  }

  void publishFullTableSyncEvents(List<String> tables) {
    int totalMessages = 0
    String migrationFlag = UUID.randomUUID().toString()

    String lastTable = tables.last()
    Long startTime = System.currentTimeMillis()

    tables.each { table ->

      String tableParameters = dataTransformer.getParameters(table)
      List<Map> messages = buildSyncEvents(table, tableParameters, migrationFlag, lastTable, startTime)

      totalMessages += messages.size()
      log.info "will send ${messages.size()} messages for table : ${table}"
      publishSyncEvents(messages)
    }
    log.info "A total of ${totalMessages} messages was sent."
  }

  List<Map> buildSyncEvents(String table, String tableParameters, String migrationId, String lastTable, Long startTime) {
    List<Map> messages = []
    if (!tableParameters.trim().isEmpty()) {

      log.info "Table ${table} has the following parameters: ${tableParameters}"
      String query = "SELECT DISTINCT ${tableParameters}  FROM ${table}"
      List<Map> parameterCombinations = sourceTemplate.queryForList(query, [:])
      int count = 0
      Map lastRow = parameterCombinations ? parameterCombinations.last() : null

      parameterCombinations.each { Map row ->
        count++
        row.put('stat', table)
        row.put('migrationFlag', migrationId)

        if (table.equals(lastTable) & row.equals(lastRow)) {
          row.put('migrationLastFlag', startTime)
        }
        messages.add(row)
      }
    } else {
      log.info "Table  ${table} has no parameters "
      Map parameters = [:]

      if (table.equals(lastTable)) {
        parameters.put('migrationLastFlag', startTime)
      }

      parameters['stat'] = table
      parameters.put('migrationFlag', migrationId)
      messages.add(parameters)
    }
    messages
  }

  void publishSyncEvents(List<Map> messages) {
    messages.each { message ->
      try {
        String jsonMessage = new JsonBuilder(message).toPrettyString()
        rabbitTemplate.convertAndSend(AmqpConfiguration.OPTIMUS_SYNC_EXCHANGE, '', jsonMessage)
      } catch (Exception e) {
        log.error("Could not send message", e)
      }

    }
  }

  @Scheduled(cron = '0 0 11 ? * *')
  void createSyncStatusReport() {
    createSyncStatusReport(getOptimusTables(), null, null)
  }

  void createMigrationStatusReport(String migrationId, Long migrationStartTime) {
    createSyncStatusReport(getOptimusTables(), migrationId, migrationStartTime)
  }

  public void createSyncStatusReport(List<String> tables, String migrationId, Long migrationStartTime) {
    tables.each {
      createSyncStatusReport(it, migrationId, migrationStartTime)
    }
  }

  void createSyncStatusReport(String table, String migrationId, Long startTime) {
    String query = "SELECT COUNT(*) as rowTotal FROM ${table}"
    Long einflussCount = 0L
    Long ulamaCount = 0L

    try {
      Map einfluss = sourceTemplate.queryForMap(query, [:])
      einflussCount = einfluss.rowTotal
    } catch (Exception e) {
      log.error "Could not  get table count from einfluss for table '${table}'", e
    }

    try {
      Map ulama = destinationTemplate.queryForMap(query, [:])
      ulamaCount = ulama.rowTotal
    } catch (Exception e) {
      log.error "Could not  get table count from ulama for table '${table}'", e
    }
    insertTableCount(migrationId, table, einflussCount, ulamaCount, startTime)
  }

  void insertTableCount(String migrationId, String table, long totalEinfluss, long totalUlama, Long startTime) {
    try {
      double successPercentage = ((double) totalUlama / (double) totalEinfluss) * 100.0
      double errorPercentage = 100.0 - successPercentage
      boolean success = errorPercentage <= allowedErrorPercentage

      Map args = [
        migrationId       : migrationId,
        table             : table,
        status            : success ? 'success' : 'error',
        errorPercentage   : errorPercentage,
        totalEinfluss     : totalEinfluss,
        totalUlama        : totalUlama,
        migrationStartDate: startTime ? new Date(startTime) : null,
        statusDate        : new Date()
      ]

      destinationTemplate.update('''INSERT INTO optimus_sync_status (migration_id ,table_name ,status,error_percentage ,total_einfluss,total_ulama ,migration_start_date, status_date )
          VALUES (:migrationId, :table, :status, :errorPercentage, :totalEinfluss, :totalUlama, :migrationStartDate ,:statusDate)''', args)

      if (success) {
        log.info "OK -> ${table}"
      } else {
        log.warn "FAIL -> ${table} "
        sendAlarm(table, errorPercentage, totalEinfluss, totalUlama)
      }

    } catch (Exception ae) {
      log.error "Could not insert to  optimus_table_error_log  for table ${table} ", ae
    }

  }

  void sendAlarm(String table, double errorPercentage, long totalEinfluss, long totalUlama) {
    try {
      long diff = totalEinfluss - totalUlama
      serviceRequester.sendRequest('EmailService', 'sendEmail', [
        subject: "Ulama Optimus sync Alarm [${diff} rows missing for ${table}]",
        body   : "Table '${table}' has ${diff} rows missing.\n\tEinfluss[ ${totalEinfluss} rows]\n\tUlama[${totalUlama} rows]\n\tError[${errorPercentage}%]\n\nVerify that einfluss is sending all the events."
      ])
    } catch (Exception e) {
      log.error "Could not send optimus sync alarm.", e
    }

  }

}