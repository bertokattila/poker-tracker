package hu.bertokattila.pt.user.service;


import hu.bertokattila.pt.user.data.UserRepository;
import hu.bertokattila.pt.user.model.User;
import hu.bertokattila.pt.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
  private final UserRepository repository;
  @Autowired
  public UserService(UserRepository repository){
    this.repository = repository;
  }

  @Transactional
  public void saveUser(UserDTO user){
    repository.save(new User(user));
  }

  public User getUserByEmail(String email){
    return repository.findIUserByEmail(email);
  }

}
