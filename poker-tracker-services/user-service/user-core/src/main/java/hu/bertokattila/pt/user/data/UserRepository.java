package hu.bertokattila.pt.user.data;

import hu.bertokattila.pt.user.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
  @Query(value = "select * from users where email = ?1", nativeQuery = true)
  User findIUserByEmail(String email);

  User findUserById(Integer id);
}
