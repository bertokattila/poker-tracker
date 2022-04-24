package hu.bertokattila.pt.session.service;


import hu.bertokattila.pt.auth.AuthUser;
import hu.bertokattila.pt.session.GetSessionsDTO;
import hu.bertokattila.pt.session.SessionDTO;
import hu.bertokattila.pt.session.data.LocationRepository;
import hu.bertokattila.pt.session.data.SessionRepository;
import hu.bertokattila.pt.session.model.Session;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SessionService {
  private final SessionRepository repository;
  private final LocationService locationService;

  @Autowired
  public SessionService(SessionRepository sessionRepository, LocationService locationService) {
    repository = sessionRepository;
    this.locationService = locationService;
  }

  public void saveSession(SessionDTO sessionDTO){
    Long locationId = null;
    if(sessionDTO.getLocation() != null){
      locationId = locationService.getLocationIdByName(sessionDTO.getLocation());
    }
    int id = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    Session session = new Session(sessionDTO, locationId, id);
    repository.save(session);
    refreshStatistics(id);
  }

  public Optional<Session> getSession(int id){
    return repository.findById(id);
  }

  public void deleteSession(int id){
    repository.deleteById(id);
    int userId= ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    refreshStatistics(userId);
  }

  public void updateSession(Session session, SessionDTO update){
    session.setComment(update.getComment());
    session.setBuyIn(update.getBuyIn());
    session.setCashOut(update.getCashOut());
    session.setCurrency(update.getCurrency());
    //session.setLocationId(update.getLocationId());
    session.setEndDate(update.getEndDate());
    session.setType(update.getType());
    session.setStartDate(update.getStartDate());
    repository.save(session);
    int userId= ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    refreshStatistics(userId);
  }

  public List<SessionDTO> getSessionsForLoggedInUser(GetSessionsDTO getSessionsDTO){
    int id = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    List<SessionRepository.sessionQuery> res = repository.findAllByUserId(id, getSessionsDTO.getLimit(), getSessionsDTO.getOffset());
    return convertSessionQuerytoSessionDto(res);
  }
  private static List<SessionDTO> convertSessionQuerytoSessionDto(List<SessionRepository.sessionQuery> res){
    List<SessionDTO> sessionDTOs = new ArrayList<>();
    for(SessionRepository.sessionQuery sessionQuery : res){
      SessionDTO dto = new SessionDTO();
      dto.setBuyIn(sessionQuery.getBuyIn());
      dto.setCashOut(sessionQuery.getCashOut());
      dto.setComment(sessionQuery.getComment());
      dto.setCurrency(sessionQuery.getCurrency());
      dto.setEndDate(sessionQuery.getEndDate());
      dto.setLocation(sessionQuery.getLocation());
      dto.setStartDate(sessionQuery.getStartDate());
      dto.setType(sessionQuery.getType());
      sessionDTOs.add(dto);
    }
    return sessionDTOs;
  }
  private static SessionDTO[] convertSessionQuerytoSessionDtoArray(List<SessionRepository.sessionQuery> res){
    List<SessionDTO> sessionDTOs = new ArrayList<>();
    for(SessionRepository.sessionQuery sessionQuery : res){
      SessionDTO dto = new SessionDTO();
      dto.setBuyIn(sessionQuery.getBuyIn());
      dto.setCashOut(sessionQuery.getCashOut());
      dto.setComment(sessionQuery.getComment());
      dto.setCurrency(sessionQuery.getCurrency());
      dto.setEndDate(sessionQuery.getEndDate());
      dto.setLocation(sessionQuery.getLocation());
      dto.setStartDate(sessionQuery.getStartDate());
      dto.setType(sessionQuery.getType());
      sessionDTOs.add(dto);
    }
    return sessionDTOs.toArray(new SessionDTO[0]);
  }
  public SessionDTO[] getSessionsForUser(int userID){
    List<SessionRepository.sessionQuery> res = repository.findAllByUserId(userID);
    return convertSessionQuerytoSessionDtoArray(res);
  }

  public void refreshStatistics(int userId){
    RestTemplate restTemplate = new RestTemplate();
    String fooResourceUrl
            = "http://localhost:5555/statistics/refresh/";
    ResponseEntity<?> response
            = restTemplate.postForObject(fooResourceUrl + userId, null, ResponseEntity.class);

  }
}
