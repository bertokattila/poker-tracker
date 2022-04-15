package hu.bertokattila.pt.user.service;

import hu.bertokattila.pt.auth.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
  private final UserService userService;
  @Autowired
   public UserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    hu.bertokattila.pt.user.model.User user = userService.getUserByEmail(email);
    return new AuthUser(user.getEmail(), user.getPassword(), new ArrayList<>(), user.getId(), user.getName());
  }
}
