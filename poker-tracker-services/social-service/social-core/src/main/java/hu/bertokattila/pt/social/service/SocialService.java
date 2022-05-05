package hu.bertokattila.pt.social.service;


import hu.bertokattila.pt.auth.AuthUser;
import hu.bertokattila.pt.social.config.ServiceUrlProperties;
import hu.bertokattila.pt.social.data.SocialConnectionRepository;
import hu.bertokattila.pt.social.model.SocialConnectionRec;
import hu.bertokattila.pt.user.UserDTO;
import hu.bertokattila.pt.user.UserIdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

public class SocialService {
  private final SocialConnectionRepository repository;
  private final ServiceUrlProperties serviceUrlProperties;
  @Autowired
  public SocialService(SocialConnectionRepository repository, ServiceUrlProperties serviceUrlProperties) {
    this.repository = repository;
    this.serviceUrlProperties = serviceUrlProperties;
  }

  public ResponseEntity<?> addFriend(String email){
    int currentUserId = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    Integer id = getIdForEmailFromUserService(email);
    if(id == null){
      return ResponseEntity.badRequest().body("User with email " + email + " does not exist");
    }
    SocialConnectionRec rec = new SocialConnectionRec();
    rec.setMasterUserId(currentUserId);
    rec.setSlaveUserId(id);
    rec.setActive(false);
    return new ResponseEntity<>(repository.save(rec), HttpStatus.OK);
  }
  public int getIdForEmailFromUserService(String email){
    RestTemplate restTemplate = new RestTemplate();
    String url = serviceUrlProperties.getUserServiceUrl();
    ResponseEntity<UserIdDTO> response = restTemplate.getForEntity(url + "/id?email=" + email, UserIdDTO.class);
    UserIdDTO user = response.getBody();
    return user.getId();
  }
}
