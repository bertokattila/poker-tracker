package hu.bertokattila.pt.auth;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AuthUser extends User {

  private int id;
  private String name;
  private String defaultCurrency;

  public AuthUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
                  int id, String name, String defaultCurrency) {
    super(username, password, authorities);
    setId(id);
    setName(name);
    setDefaultCurrency(defaultCurrency);
  }

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

  public String getDefaultCurrency() {
    return defaultCurrency;
  }

  public void setDefaultCurrency(String defaultCurrency) {
    this.defaultCurrency = defaultCurrency;
  }
}
