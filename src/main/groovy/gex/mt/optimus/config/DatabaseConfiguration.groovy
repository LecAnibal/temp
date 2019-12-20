package gex.mt.optimus.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

/**
 * Created by angelpimentel on 8/18/15.
 */

@Configuration
class DatabaseConfiguration {
  @Bean(name = 'statsDataSource')
  @ConfigurationProperties(prefix = 'spring.stats.ds')
  @Primary
  DataSource statsDataSource() {
    DataSourceBuilder.create().build()
  }

  @Bean(name = 'statsJdbcTemplate')
  NamedParameterJdbcTemplate statsJdbcTemplate(@Qualifier('statsDataSource') DataSource statsDataSource) {
    new NamedParameterJdbcTemplate(statsDataSource)
  }

  @Bean(name = 'einflussDataSource')
  @ConfigurationProperties(prefix = 'spring.einfluss.ds')
  DataSource einflussDataSource() {
    DataSourceBuilder.create().build()
  }

  @Bean(name = 'einflussJdbcTemplate')
  NamedParameterJdbcTemplate einflussJdbcTemplate(@Qualifier('einflussDataSource') DataSource einflussDataSource) {
    new NamedParameterJdbcTemplate(einflussDataSource)
  }
}
