package gex.mt.optimus.amqp

import com.google.gson.Gson
import gex.mt.optimus.config.AmqpConfiguration
import gex.mt.optimus.service.OptimusSyncService
import gex.mt.optimus.service.DataTransformer
import groovy.util.logging.Slf4j
import org.springframework.amqp.core.Message
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

@Slf4j
@Component
class SynchronizationListener {

  public static final String MIGRATION_COMMAND = 'migrate'

  public static final String SYNC_STATUS_COMMAND = 'status'

  @Autowired
  DataTransformer dataTransformer
  @Autowired
  Gson gson

  @Autowired
  OptimusSyncService syncService

  @Value('${optimus.delay.seconds:10}')
  Long delaySeconds

  ScheduledExecutorService delayedExecutor = Executors.newScheduledThreadPool(10)

  @PostConstruct
  void init() {
    if (delaySeconds) {
      log.info '\n\nSynchronization Delay is set to  {} seconds !!!!\n\n', delaySeconds
    }
  }

  @RabbitListener(queues = AmqpConfiguration.OPTIMUS_SYNC_QUEUE)
  void onMessage(Message message) {
    try {
      Map parameters = gson.fromJson(new String(message.body), Map)
      delayedExecutor.schedule(new OptimusTask(parameters: parameters), (parameters.migrationFlag ? 0L : delaySeconds), TimeUnit.SECONDS)
    } catch (Exception ex) {
      log.error "Cannot consume message ${new String(message.body)}", ex
    }
  }

  class OptimusTask implements Runnable {

    Map parameters

    @Override
    void run() {
      try {
        if (MIGRATION_COMMAND == parameters.command) {
          if(parameters.migrationTables) {
            syncService.syncTables(parameters.migrationTables)
          } else {
            syncService.syncAllTables()
          }
        } else if (SYNC_STATUS_COMMAND == parameters.command){
          syncService.createSyncStatusReport()
        }else{
          dataTransformer.copy(parameters.stat, parameters)
        }
      } catch (Exception e) {
        log.error("Unexpected exception processing optimus message: ${parameters}\n", e)
      }
    }
  }
}
