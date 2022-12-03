package hu.bertokattila.pt.session.data;

import hu.bertokattila.pt.session.model.Location;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, UUID> {
  Optional<Location> findLocationByName(String name);
}
