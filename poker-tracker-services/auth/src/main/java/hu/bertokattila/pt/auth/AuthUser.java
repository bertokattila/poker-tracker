package hu.bertokattila.pt.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AuthUser extends User {

  private int id;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String name;

  public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities, int id, String name) {
    super(username, password, authorities);
    setId(id);
    setName(name);
  }
}