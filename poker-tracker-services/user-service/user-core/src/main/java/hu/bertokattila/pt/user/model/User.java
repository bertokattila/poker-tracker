package hu.bertokattila.pt.user.model;

import hu.bertokattila.pt.user.UserDTO;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "user")
@Table(name = "users")
public class User {


  public User(UserDTO dto){
    this.name = dto.getName();
    this.email = dto.getEmail();
    this.password = dto.getPassword();
    this.defaultCurrency = dto.getDefaultCurrency();
  }

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

  public User() {

  }
}
