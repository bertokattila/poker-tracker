package hu.bertokattila.pt.session.service;

import hu.bertokattila.pt.session.LocationDTO;
import hu.bertokattila.pt.session.data.LocationRepository;
import hu.bertokattila.pt.session.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class LocationService {
  private final LocationRepository repository;
  @Autowired
  public LocationService(LocationRepository locationRepository){
    repository = locationRepository;
  }

  @Transactional
  public void saveLocation(LocationDTO location){
    repository.save(new Location(location));
  }
}
