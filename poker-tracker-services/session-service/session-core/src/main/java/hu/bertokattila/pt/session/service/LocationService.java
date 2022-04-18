package hu.bertokattila.pt.session.service;

import hu.bertokattila.pt.session.LocationDTO;
import hu.bertokattila.pt.session.data.LocationRepository;
import hu.bertokattila.pt.session.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class LocationService {
  private final LocationRepository repository;
  @Autowired
  public LocationService(LocationRepository locationRepository){
    repository = locationRepository;
  }


  public void saveLocation(LocationDTO location){
    repository.save(new Location(location));
  }

  /**
   *  Returns id of the location or creates a new one if it doesn't exist
   */
  public Long getLocationIdByName(String name){
   Location location = (repository.findLocationByName(name)).orElse(null);
   if(location == null) {
     location = new Location(name);
     repository.save(location);
   }
   return location.getId();
  }

}
