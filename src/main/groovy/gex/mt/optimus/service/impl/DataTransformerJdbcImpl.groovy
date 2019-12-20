package gex.mt.optimus.service.impl

import gex.mt.optimus.service.DataCopier
import gex.mt.optimus.service.DataTransformer
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by angelpimentel on 8/21/15.
 */
@Component
@Slf4j
class DataTransformerJdbcImpl implements DataTransformer {
  @Autowired
  Map<String, DataCopier> copierMap

  @Override
  void copy(String table, Map<String, String> parameters) {
    DataCopier copier = copierMap[table]
    if (copier) {
      copier.copy(parameters)
    } else {
      log.warn('Copy configuration not found for {}', table)
    }
  }

  @Override
  String getParameters(String table) {
    DataCopier copier = copierMap[table]
    copier.getProperties().queryParameters
  }
}
