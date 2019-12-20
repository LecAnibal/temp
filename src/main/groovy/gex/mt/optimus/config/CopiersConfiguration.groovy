package gex.mt.optimus.config

import gex.mt.optimus.service.DataCopier
import gex.mt.optimus.service.impl.EinflussDataCopier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by angelpimentel on 8/27/15.
 */
@Configuration
class CopiersConfiguration {
  @Bean(name = 'mt5_stats_arbitro_torneo_jornada')
  @ConfigurationProperties(prefix = 'mt5_stats_arbitro_torneo_jornada', ignoreUnknownFields = true)
  DataCopier mt5_stats_arbitro_torneo_jornada() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_arbitro_torneos')
  @ConfigurationProperties(prefix = 'mt5_stats_arbitro_torneos', ignoreUnknownFields = true)
  DataCopier mt5_stats_arbitro_torneos() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_autogoles')
  @ConfigurationProperties(prefix = 'mt5_stats_autogoles', ignoreUnknownFields = true)
  DataCopier mt5_stats_autogoles() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_dt_torneo_jornada')
  @ConfigurationProperties(prefix = 'mt5_stats_dt_torneo_jornada', ignoreUnknownFields = true)
  DataCopier mt5_stats_dt_torneo_jornada() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_dt_torneos')
  @ConfigurationProperties(prefix = 'mt5_stats_dt_torneos', ignoreUnknownFields = true)
  DataCopier mt5_stats_dt_torneos() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_goleoindividual')
  @ConfigurationProperties(prefix = 'mt5_stats_goleoindividual', ignoreUnknownFields = true)
  DataCopier mt5_stats_goleoindividual() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_jugador')
  @ConfigurationProperties(prefix = 'mt5_stats_jugador', ignoreUnknownFields = true)
  DataCopier mt5_stats_jugador() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_jugador_jornada')
  @ConfigurationProperties(prefix = 'mt5_stats_jugador_jornada', ignoreUnknownFields = true)
  DataCopier mt5_stats_jugador_jornada() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_pasesgol')
  @ConfigurationProperties(prefix = 'mt5_stats_pasesgol', ignoreUnknownFields = true)
  DataCopier mt5_stats_pasesgol() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_penales_anotados')
  @ConfigurationProperties(prefix = 'mt5_stats_penales_anotados', ignoreUnknownFields = true)
  DataCopier mt5_stats_penales_anotados() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_penales_fallados')
  @ConfigurationProperties(prefix = 'mt5_stats_penales_fallados', ignoreUnknownFields = true)
  DataCopier mt5_stats_penales_fallados() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_tabla_defensivas')
  @ConfigurationProperties(prefix = 'mt5_stats_tabla_defensivas', ignoreUnknownFields = true)
  DataCopier mt5_stats_tabla_defensivas() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_tabla_general')
  @ConfigurationProperties(prefix = 'mt5_stats_tabla_general', ignoreUnknownFields = true)
  DataCopier mt5_stats_tabla_general() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_tabla_general_locales')
  @ConfigurationProperties(prefix = 'mt5_stats_tabla_general_locales', ignoreUnknownFields = true)
  DataCopier mt5_stats_tabla_general_locales() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_tabla_general_visitantes')
  @ConfigurationProperties(prefix = 'mt5_stats_tabla_general_visitantes', ignoreUnknownFields = true)
  DataCopier mt5_stats_tabla_general_visitantes() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_tabla_ofensivas')
  @ConfigurationProperties(prefix = 'mt5_stats_tabla_ofensivas', ignoreUnknownFields = true)
  DataCopier mt5_stats_tabla_ofensivas() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_tabla_porcentajes')
  @ConfigurationProperties(prefix = 'mt5_stats_tabla_porcentajes', ignoreUnknownFields = true)
  DataCopier mt5_stats_tabla_porcentajes() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_tarjetas_amarillas')
  @ConfigurationProperties(prefix = 'mt5_stats_tarjetas_amarillas', ignoreUnknownFields = true)
  DataCopier mt5_stats_tarjetas_amarillas() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_tarjetas_rojas')
  @ConfigurationProperties(prefix = 'mt5_stats_tarjetas_rojas', ignoreUnknownFields = true)
  DataCopier mt5_stats_tarjetas_rojas() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_defensive')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_defensive', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_defensive() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_discipline')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_discipline', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_discipline() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_goalkeepers')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_goalkeepers', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_goalkeepers() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_ofensive')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_ofensive', ignoreUnknownFields = true)
  DataCopier testComt5_stats_ulama_player_ofensivepier() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_passes')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_passes', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_passes() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_defensive')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_defensive', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_defensive() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_discipline')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_discipline', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_discipline() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_ofensive')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_ofensive', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_ofensive() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_passes')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_passes', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_passes() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_quotient')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_quotient', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_quotient() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_defensive_home')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_defensive_home', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_defensive_home() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_ofensive_home')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_ofensive_home', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_ofensive_home() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_passes_home')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_passes_home', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_passes_home() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_discipline_home')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_discipline_home', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_discipline_home() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_defensive_away')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_defensive_away', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_defensive_away() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_ofensive_away')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_ofensive_away', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_ofensive_away() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_passes_away')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_passes_away', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_passes_away() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_team_discipline_away')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_team_discipline_away', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_team_discipline_away() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_defensive_home')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_defensive_home', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_defensive_home() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_discipline_home')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_discipline_home', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_discipline_home() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_goalkeepers_home')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_goalkeepers_home', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_goalkeepers_home() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_ofensive_home')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_ofensive_home', ignoreUnknownFields = true)
  DataCopier testComt5_stats_ulama_player_ofensive_homepier() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_passes_home')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_passes_home', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_passes_home() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_defensive_away')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_defensive_away', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_defensive_away() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_discipline_away')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_discipline_away', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_discipline_away() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_goalkeepers_away')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_goalkeepers_away', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_goalkeepers_away() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_ofensive_away')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_ofensive_away', ignoreUnknownFields = true)
  DataCopier testComt5_stats_ulama_player_ofensive_awaypier() { new EinflussDataCopier() }

  @Bean(name = 'mt5_stats_ulama_player_passes_away')
  @ConfigurationProperties(prefix = 'mt5_stats_ulama_player_passes_away', ignoreUnknownFields = true)
  DataCopier mt5_stats_ulama_player_passes_away() { new EinflussDataCopier() }

}
