package hu.bertokattila.pt.session.data;

import hu.bertokattila.pt.session.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends CrudRepository<Location, UUID> {
}
