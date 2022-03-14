package hu.bertokattila.pt.session.controllers;

import hu.bertokattila.pt.session.LocationDTO;
import hu.bertokattila.pt.session.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/location")
public class LocationController {

  private final LocationService locationService;

  @Autowired
  public LocationController(LocationService locationService){
    this.locationService = locationService;
  }

  @PostMapping
  public void saveLocation(@RequestBody LocationDTO location){
    locationService.saveLocation(location);
  }
}
