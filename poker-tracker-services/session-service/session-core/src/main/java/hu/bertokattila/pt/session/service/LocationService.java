package hu.bertokattila.pt.session.service;

import hu.bertokattila.pt.session.LocationDTO;
import hu.bertokattila.pt.session.data.LocationRepository;
import hu.bertokattila.pt.session.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
  private LocationRepository repository;
  @Autowired
  public LocationService(LocationRepository locationRepository){
    repository = locationRepository;
  }

  public void hello(LocationDTO location){

    repository.save(new Location(location));
    System.out.println(location.toString());
  }
}
