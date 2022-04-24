package hu.bertokattila.pt.statistics.data;


import hu.bertokattila.pt.statistics.model.GenericStatisticsRec;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GenericStatisticsRepository extends CrudRepository<GenericStatisticsRec, Integer> {
  @Query(value= "SELECT * FROM generic_statistics WHERE userid = ?1", nativeQuery = true)
  public Optional<GenericStatisticsRec> findByUserId(int userId);
}
