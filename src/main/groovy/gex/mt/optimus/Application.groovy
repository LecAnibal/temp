package gex.mt.optimus

import static org.springframework.boot.SpringApplication.run

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

/**
 * Created by angelpimentel on 8/21/15.
 */
@SpringBootApplication
@ComponentScan('gex.mt')
@EnableScheduling
class Application {

  static void main(String[] args) {
    run Application, args
  }
}
