package hu.bertokattila.pt.statistics.data;


import hu.bertokattila.pt.statistics.model.GenericStatisticsRec;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GenericStatisticsRepository extends CrudRepository<GenericStatisticsRec, Integer> {
  @Query(value = "SELECT * FROM generic_statistics WHERE userid = ?1", nativeQuery = true)
  Optional<GenericStatisticsRec> findByUserId(int userId);
}
