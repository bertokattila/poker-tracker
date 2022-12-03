package hu.bertokattila.pt.user.service;


import hu.bertokattila.pt.user.UserDTO;
import hu.bertokattila.pt.user.data.UserRepository;
import hu.bertokattila.pt.user.model.User;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public void saveUser(UserDTO user) {
    repository.save(new User(user));
  }

  public User getUserByEmail(String email) {
    return repository.findIUserByEmail(email);
  }

  public User getUserById(int id) {
    return repository.findUserById(id);
  }

}
