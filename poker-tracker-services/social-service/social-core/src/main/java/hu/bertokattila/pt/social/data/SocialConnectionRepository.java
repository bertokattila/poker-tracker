package hu.bertokattila.pt.social.data;

import hu.bertokattila.pt.social.model.SocialConnectionRec;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SocialConnectionRepository extends JpaRepository<SocialConnectionRec, Integer> {

  @Query(value= "SELECT * FROM social WHERE (slaveuserid = ?1 AND masteruserid = ?2) OR (slaveuserid = ?2 AND masteruserid = ?1)", nativeQuery = true)
  Optional<SocialConnectionRec> findByMasterUserIdAndSlaveUserId(int masteruserId, int slaveuserId);
}
