package hu.bertokattila.pt.session.service;

import hu.bertokattila.pt.session.SessionDTO;
import hu.bertokattila.pt.session.data.SessionRepository;
import hu.bertokattila.pt.session.model.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class SessionService {
  private final SessionRepository repository;
  @Autowired
  public SessionService(SessionRepository sessionRepository){
    repository = sessionRepository;
  }

  @Transactional
  public void saveSession(SessionDTO sessionDTO){
    Session session = new Session(sessionDTO, 1);
    repository.save(session);
  }

  @Transactional
  public Optional<Session> getSession(int id){
    return repository.findById(id);
  }
  @Transactional
  public void deleteSession(int id){
    repository.deleteById(id);
  }
  @Transactional
  public void updateSession(Session session, SessionDTO update){
    session.setComment(update.getComment());
    session.setBuyIn(update.getBuyIn());
    session.setCashOut(update.getCashOut());
    session.setCurrency(update.getCurrency());
    session.setLocationId(update.getLocationId());
    session.setEndDate(update.getEndDate());
    session.setType(update.getType());
    session.setStartDate(update.getStartDate());
    repository.save(session);
  }
}
