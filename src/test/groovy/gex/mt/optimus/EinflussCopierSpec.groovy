package gex.mt.optimus

import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Ignore
import spock.lang.IgnoreRest
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import javax.sql.DataSource

/**
 * Created by LecAnibal on 01/09/15.
 */
@Ignore
@ContextConfiguration(loader = SpringApplicationContextLoader, classes = CoreTestConfig)
class EinflussCopierSpec extends Specification {

  @Autowired
  RabbitTemplate rabbitTemplate

  @Autowired
  @Qualifier('statsDataSource')
  DataSource statsDataSource

  String exchangeName = 'synchronizationExchange'


  def 'We can copy mt5_stats_arbitro_torneo_jornada from mysql to postgres'() {
    setup:
           def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_arbitro_torneo_jornada","id_torneo":26}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_arbitro_torneo_jornada')
    }
  }

  def 'We can copy mt5_stats_arbitro_torneos from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_arbitro_torneos","id_torneo":25}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_arbitro_torneos')

    }
  }

  def 'We can copy mt5_stats_autogoles from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_autogoles","id_torneo":25}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_autogoles')
    }
  }

  def 'We can copy mt5_stats_dt_torneo_jornada from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_dt_torneo_jornada","id_torneo":25}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_dt_torneo_jornada')

    }
  }

  def 'We can copy mt5_stats_escuderia from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_escuderia"}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_escuderia')

    }
  }

  def 'We can copy mt5_stats_dt_torneos from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_dt_torneos","id_torneo":30}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_dt_torneos')

    }
  }

  def 'We can copy mt5_stats_escuderia_carrera from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_escuderia_carrera"}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_escuderia_carrera')

    }
  }

  def 'We can copy mt5_stats_goleoindividual from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_goleoindividual","id_torneo":25}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_goleoindividual')

    }
  }

  def 'We can copy mt5_stats_jugador from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_jugador","id_torneo":25}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_jugador')

    }
  }

  def 'We can copy mt5_stats_jugador_jornada from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:

         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_jugador_jornada","id_torneo":25,"id_fase":25,"jornada":25}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_jugador_jornada')

    }
  }

  def 'We can copy mt5_stats_penales_anotados from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_penales_anotados","id_torneo":25}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_penales_anotados')

    }
  }

  def 'We can copy mt5_stats_penales_fallados from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_penales_fallados","id_torneo":25}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_penales_fallados')

    }
  }

  def 'We can copy mt5_stats_piloto from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_piloto"}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_piloto')

    }
  }

  def 'We can copy mt5_stats_piloto_carrera from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_piloto_carrera"}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_piloto_carrera')

    }
  }

  def 'We can copy mt5_stats_tabla_defensivas from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_tabla_defensivas","id_torneo":25}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_tabla_defensivas')

    }
  }

  def 'We can copy mt5_stats_tabla_general from mysql to postgres'() {
    setup:
         def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
         rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_tabla_general","id_torneo":1,"jornada":2,"id_fase":3}')

    then:
         conditions.eventually {
      tableHasData('mt5_stats_tabla_general')

    }
  }

  def 'We can copy mt5_stats_tabla_general_locales from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_tabla_general_locales","id_torneo":1,"jornada":2,"id_fase":3}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_tabla_general_locales')

    }
  }

  def 'We can copy mt5_stats_tabla_general_tmp from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_tabla_general_tmp"}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_tabla_general_tmp')

    }
  }

  def 'We can copy mt5_stats_tabla_general_visitantes from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_tabla_general_visitantes","id_torneo":1,"jornada":2,"id_fase":3}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_tabla_general_visitantes')

    }
  }

  def 'We can copy mt5_stats_tabla_ofensivas from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_tabla_ofensivas","id_torneo":25}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_tabla_ofensivas')

    }
  }

  def 'We can copy mt5_stats_tabla_porcentajes from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_tabla_porcentajes","id_torneo":25}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_tabla_porcentajes')

    }
  }

  def 'We can copy mt5_stats_tarjetas_amarillas from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_tarjetas_amarillas","id_torneo":25}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_tarjetas_amarillas')

    }
  }

  def 'We can copy mt5_stats_tarjetas_rojas from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_tarjetas_rojas","id_torneo":25}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_tarjetas_rojas')

    }
  }

  def 'We can copy mt5_stats_ulama_player_defensive from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_defensive","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_defensive')

    }
  }



  def 'We can copy mt5_stats_ulama_player_discipline from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_discipline","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_discipline')

    }
  }


  def 'We can copy mt5_stats_ulama_player_goalkeepers from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_goalkeepers","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_goalkeepers')

    }
  }



  def 'We can copy mt5_stats_ulama_player_ofensive from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_ofensive","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_ofensive')

    }
  }


  def 'We can copy mt5_stats_ulama_player_passes from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_passes","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_passes')

    }
  }


  def 'We can copy mt5_stats_ulama_team_defensive from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_defensive","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_defensive')

    }
  }


  def 'We can copy mt5_stats_ulama_team_discipline from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_discipline","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_discipline')

    }
  }


  def 'We can copy mt5_stats_ulama_team_ofensive from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_ofensive","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_ofensive')

    }
  }


  def 'We can copy mt5_stats_ulama_team_passes from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_passes","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_passes')

    }
  }

  def 'We can copy mt5_stats_ulama_team_quotient from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_quotient"}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_quotient')

    }
  }



  def 'We can copy mt5_torneos from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:

    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_torneos"}')

    then:
    conditions.eventually {
      tableHasData('seasons where seasons.champion_id NOTNULL')
    }
  }

  def 'We can copy mt5_stats_ulama_team_defensive_home from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_defensive_home"}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_defensive_home')

    }
  }

  def 'We can copy mt5_stats_ulama_team_ofensive_home from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_ofensive_home"}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_ofensive_home')

    }
  }

  def 'We can copy mt5_stats_ulama_team_passes_home from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_passes_home"}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_passes_home')

    }
  }

  def 'We can copy mt5_stats_ulama_team_discipline_home from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_discipline_home"}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_discipline_home')

    }
  }

  def 'We can copy mt5_stats_ulama_team_defensive_away from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_defensive_away"}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_defensive_away')

    }
  }

  def 'We can copy mt5_stats_ulama_team_ofensive_away from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_ofensive_away"}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_ofensive_away')

    }
  }

  def 'We can copy mt5_stats_ulama_team_passes_away from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_passes_away"}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_passes_away')

    }
  }

  def 'We can copy mt5_stats_ulama_team_discipline_away from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_team_discipline_away"}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_team_discipline_away')

    }
  }

  def 'We can copy mt5_stats_ulama_player_defensive_home from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_defensive_home","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_defensive_home')

    }
  }



  def 'We can copy mt5_stats_ulama_player_discipline_home from mysql to postgres'() {

    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:

    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_discipline_home","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_discipline_home')

    }
  }


  def 'We can copy mt5_stats_ulama_player_goalkeepers_home from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_goalkeepers_home","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_goalkeepers_home')

    }
  }



  def 'We can copy mt5_stats_ulama_player_ofensive_home from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_ofensive_home","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_ofensive_home')

    }
  }


  def 'We can copy mt5_stats_ulama_player_passes_home from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_passes_home","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_passes_home')

    }
  }

  def 'We can copy mt5_stats_ulama_player_defensive_away from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_defensive_away","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_defensive_away')

    }
  }



  def 'We can copy mt5_stats_ulama_player_discipline_away from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_discipline_away","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_discipline_away')

    }
  }


  def 'We can copy mt5_stats_ulama_player_goalkeepers_away from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_goalkeepers_away","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_goalkeepers_away')

    }
  }



  def 'We can copy mt5_stats_ulama_player_ofensive_away from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_ofensive_away","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_ofensive_away')

    }
  }


  def 'We can copy mt5_stats_ulama_player_passes_away from mysql to postgres'() {
    setup:
    def conditions = new PollingConditions(timeout: 10, initialDelay: 1.5)

    when:
    rabbitTemplate.convertAndSend(exchangeName, '', '{"stat":"mt5_stats_ulama_player_passes_away","competition_id":5,"season_id":15}')

    then:
    conditions.eventually {
      tableHasData('mt5_stats_ulama_player_passes_away')

    }
  }


  public boolean tableHasData(String tableName) {
    Sql sql = new Sql(statsDataSource)
    String query = "SELECT COUNT(*) as C FROM ${tableName}".toString()

    int resulset = sql.firstRow(query).C

    println "Cantidad  de  registos  ${tableName} : "+resulset

    resulset > 0
  }



}
