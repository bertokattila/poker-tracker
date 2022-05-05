package hu.bertokattila.pt.session.data;

import hu.bertokattila.pt.session.SessionDTO;
import hu.bertokattila.pt.session.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
  @Query(value= "SELECT session.*, location.name as location FROM session left join location on session.locationid = location.id WHERE userId = ?1 ORDER BY enddate DESC LIMIT ?2 OFFSET ?3", nativeQuery = true)
  List<sessionQuery> findAllByUserId(int id, int limit, int offset);

  @Query(value= "SELECT session.*, location.name as location FROM session left join location on session.locationid = location.id WHERE userId = ?1 ORDER BY enddate", nativeQuery = true)
  List<sessionQuery> findAllByUserId(int id);

  public static interface sessionQuery {
     String getType();
     String getCurrency();
     double getBuyIn();
     double getCashOut();
     LocalDateTime getStartDate();
     LocalDateTime getEndDate();
     String getComment();
     String getLocation();
     String getAccess();
  }

}
