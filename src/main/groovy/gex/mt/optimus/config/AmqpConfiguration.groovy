package gex.mt.optimus.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by angelpimentel on 8/21/15.
 */
@Configuration
class AmqpConfiguration {


  public static final String OPTIMUS_SYNC_EXCHANGE = 'ulama.optimus.sync'

  public static final String OPTIMUS_SYNC_QUEUE = 'ulama.optimus.sync.events'

  @Bean
  Queue synchronizationQueue() {
    new Queue(OPTIMUS_SYNC_QUEUE)

  }

  @Bean
  FanoutExchange synchronizationExchange() {
    new FanoutExchange(OPTIMUS_SYNC_EXCHANGE)
  }

  @Bean
  Binding synchronizationBinding() {
    BindingBuilder.bind(synchronizationQueue()).to(synchronizationExchange())
  }
}
