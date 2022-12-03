package hu.bertokattila.pt.statistics.data;


import hu.bertokattila.pt.statistics.model.StatisticsHistoryRec;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StatisticsHistoryRepository extends CrudRepository<StatisticsHistoryRec, Integer> {
  @Query(value = "SELECT SUM(result) AS result, SUM(playedtime) AS palyedtime, to_char(startdate, 'YYYY') AS year, type from statistics_history where userid = ?1 group by to_char(startdate, 'YYYY'), type ORDER BY year ASC", nativeQuery = true)
  List<StatQuery> getYearlyStats(int userId);

  @Query(value = "SELECT SUM(result) as result, sum(playedtime) as palyedtime, to_char(startdate, 'Month') as month, type from statistics_history where userid = ?1 AND statistics_history.startdate  > date_trunc('month', CURRENT_DATE) - INTERVAL '11 months' group by to_char(startdate, 'Month'), type", nativeQuery = true)
  List<StatQueryMonthly> getMonthlyStats(int userId);

  List<StatisticsHistoryRec> getAllByUserId(int userId);

  StatisticsHistoryRec getBySessionId(int sessionId);

  @Query(nativeQuery = true, value = "SELECT SUM(result) as result, SUM(playedtime) as playedtime, userid as userid FROM statistics_history WHERE enddate > CURRENT_DATE - INTERVAL '30' DAY GROUP BY userid")
  List<AggrQuery> getAggrResultAndPlayedTimeLastMonth();

  @Query(nativeQuery = true, value = "SELECT SUM(result) as result, SUM(playedtime) as playedtime, userid as userid FROM statistics_history WHERE enddate > CURRENT_DATE - INTERVAL '365' DAY GROUP BY userid")
  List<AggrQuery> getAggrResultAndPlayedTimeLastYear();

  @Query(nativeQuery = true, value = "SELECT SUM(result) as result, SUM(playedtime) as playedtime, userid as userid FROM statistics_history GROUP BY userid")
  List<AggrQuery> getAggrResultAndPlayedTimeAllTime();

  interface StatQuery {
    int getResult();

    int getPlayedTime();

    String getYear();

    String getType();
  }

  interface StatQueryMonthly {
    int getResult();

    int getPlayedTime();

    String getMonth();

    String getType();
  }

  interface AggrQuery {
    int getResult();

    int getPlayedTime();

    int getUserId();
  }
}
