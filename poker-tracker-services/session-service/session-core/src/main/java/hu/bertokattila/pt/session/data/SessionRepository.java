package hu.bertokattila.pt.session.data;

import hu.bertokattila.pt.session.model.Session;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends CrudRepository<Session, Integer> {
  @Query(value= "SELECT * FROM session WHERE userId = ?1 ORDER BY enddate DESC LIMIT ?2 OFFSET ?3", nativeQuery = true)
  List<Session> findAllByUserId(int id, int limit, int offset);
}
