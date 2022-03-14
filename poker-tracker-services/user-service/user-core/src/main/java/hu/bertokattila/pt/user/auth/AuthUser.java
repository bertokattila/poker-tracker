package hu.bertokattila.pt.user.auth;

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

  public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities, int id) {
    super(username, password, authorities);
    setId(id);
  }
}