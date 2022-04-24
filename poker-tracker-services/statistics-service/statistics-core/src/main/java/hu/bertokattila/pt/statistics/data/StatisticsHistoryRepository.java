package hu.bertokattila.pt.statistics.data;


import hu.bertokattila.pt.statistics.model.StatisticsHistoryRec;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StatisticsHistoryRepository extends CrudRepository<StatisticsHistoryRec, Integer> {

}
