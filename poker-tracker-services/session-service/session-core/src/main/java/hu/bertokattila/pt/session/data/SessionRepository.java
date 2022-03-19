package hu.bertokattila.pt.session.data;

import hu.bertokattila.pt.session.model.Session;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<Session, Integer> {
}
