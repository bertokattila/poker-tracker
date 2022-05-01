package hu.bertokattila.pt.session.util.entitylisteners;

import hu.bertokattila.pt.session.model.Session;
import hu.bertokattila.pt.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
public class SessionListener {

  //@PersistenceContext
  //private EntityManager entityManager;

  @PostPersist
  @PostUpdate
  @PostRemove
  private void afterAnyUpdate(Session session) {
    //entityManager.flush();
    refreshStatistics(session.getUserId());
  }

  public void refreshStatistics(int userId){
    RestTemplate restTemplate = new RestTemplate();
    String refreshUrl
            = "http://localhost:5555/statistics/refresh/";
    ResponseEntity<?> response
            = restTemplate.postForObject(refreshUrl + userId, null, ResponseEntity.class);

  }
}