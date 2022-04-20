package hu.bertokattila.pt.session.service;


import hu.bertokattila.pt.auth.AuthUser;
import hu.bertokattila.pt.session.GetSessionsDTO;
import hu.bertokattila.pt.session.SessionDTO;
import hu.bertokattila.pt.session.data.LocationRepository;
import hu.bertokattila.pt.session.data.SessionRepository;
import hu.bertokattila.pt.session.model.Session;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
  }

  public Optional<Session> getSession(int id){
    return repository.findById(id);
  }

  public void deleteSession(int id){
    repository.deleteById(id);
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
  }

  public List<Session> getSessionsForLoggedInUser(GetSessionsDTO getSessionsDTO){
    int id = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    return repository.findAllByUserId(id, getSessionsDTO.getLimit(), getSessionsDTO.getOffset());
  }
}
