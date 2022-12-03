package hu.bertokattila.pt.session.model;

import hu.bertokattila.pt.session.LocationDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "location")
@Table(name = "location")
public class Location {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "name")
  private String name;

  public Location(String name) {
    this.setName(name);
  }

  public Location(LocationDTO locationDTO) {
    this.setName(locationDTO.getName());
  }

  public Location() {

  }
}
