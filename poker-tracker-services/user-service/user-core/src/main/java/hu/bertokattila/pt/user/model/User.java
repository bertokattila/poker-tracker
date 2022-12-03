package hu.bertokattila.pt.user.model;

import hu.bertokattila.pt.user.UserDTO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "user")
@Table(name = "users")
public class User {


  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @Column(name = "name")
  private String name;
  @Column(name = "email")
  private String email;
  @Column(name = "password")
  private String password;
  @Column(name = "defaultcurrency")
  private String defaultCurrency;

  public User(UserDTO dto) {
    this.name = dto.getName();
    this.email = dto.getEmail();
    this.password = dto.getPassword();
    this.defaultCurrency = dto.getDefaultCurrency().toUpperCase();
  }

  public User() {

  }
}
