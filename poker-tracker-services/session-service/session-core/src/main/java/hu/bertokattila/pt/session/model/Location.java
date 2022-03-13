package hu.bertokattila.pt.session.model;

import hu.bertokattila.pt.session.LocationDTO;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity(name = "location")
@Table(name = "location")
public class Location {

  public Location(String name){
    this.setName(name);
  }
  public Location(LocationDTO locationDTO){
    this.setName(locationDTO.getName());
  }

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  public Location() {

  }
}
