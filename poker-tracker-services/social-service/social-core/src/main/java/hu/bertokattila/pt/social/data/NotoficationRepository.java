package hu.bertokattila.pt.social.data;


import hu.bertokattila.pt.social.model.NotificationRec;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotoficationRepository extends CrudRepository<NotificationRec, Integer> {

}
