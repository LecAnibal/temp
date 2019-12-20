package gex.mt.optimus.config

import com.google.gson.Gson
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by angelpimentel on 8/27/15.
 */
@Configuration
class GsonConfiguration {
  @Bean
  Gson gson() {
    new Gson()
  }
}
