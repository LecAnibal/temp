package gex.mt.optimus.service

import gex.mt.optimus.service.impl.EinflussDataCopier
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

/**
 * Created by angelpimentel on 9/2/15.
 */
@Slf4j
@Component
@Profile('initializer')
class DataInitializer {
  @Autowired
  List<EinflussDataCopier> dataCopiers

  @PostConstruct
  void init() {
    log.info('Starting data initialization')
    dataCopiers.each {
      log.info('Initializing table {}', it.stat)
      String sourceQuery = it.sourceQuery
      String setupQuery = it.setupQuery
      it.setupQuery = "Delete from ${it.stat}"
      int limite = sourceQuery.indexOf('where')
      if (limite > 1) {
        it.sourceQuery = sourceQuery.substring(0, limite)
      }
      it.copy([:])
      it.sourceQuery = sourceQuery
      it.setupQuery = setupQuery
    }
  }
}
