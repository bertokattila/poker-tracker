package hu.bertokattila.pt.session.data;

import hu.bertokattila.pt.session.model.Session;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
  @Query(value = "SELECT session.*, location.name as location FROM session left join location on session.locationid = location.id WHERE userId = ?1 ORDER BY enddate DESC LIMIT ?2 OFFSET ?3", nativeQuery = true)
  List<sessionQuery> findAllByUserId(int id, int limit, int offset);

  @Query(value = "SELECT session.*, location.name as location FROM session left join location on session.locationid = location.id WHERE userId = ?1 ORDER BY enddate", nativeQuery = true)
  List<sessionQuery> findAllByUserId(int id);

  @Query(value = "SELECT session.*, location.name as location FROM session left join location on session.locationid = location.id WHERE userId IN ?1 AND access = ?4 ORDER BY enddate DESC LIMIT ?2 OFFSET ?3", nativeQuery = true)
  List<sessionQuery> findAllByUserIdsAndAccess(List<Integer> ids, int limit, int offset, String access);

  interface sessionQuery {
    int getId();

    int getUserId();

    String getType();

    String getCurrency();

    double getBuyIn();

    double getCashOut();

    LocalDateTime getStartDate();

    LocalDateTime getEndDate();

    String getComment();

    String getLocation();

    String getAccess();

    Double getAnte();

    String getBlinds();

    Integer getTableSize();

    String getGame();

    Double getExchangeRate();
  }

}
