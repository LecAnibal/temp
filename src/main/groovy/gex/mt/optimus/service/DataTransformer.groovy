package gex.mt.optimus.service
/**
 * Created by angelpimentel on 8/21/15.
 */
interface DataTransformer {
  void copy(String table, Map<String, String> parametros)

  String getParameters(String table)
}
