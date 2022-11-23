package hu.bertokattila.pt.session.service;


import hu.bertokattila.pt.auth.AuthUser;
import hu.bertokattila.pt.session.ExtendedSessionDTO;
import hu.bertokattila.pt.session.GetSessionsDTO;
import hu.bertokattila.pt.session.PublicSessionsDTO;
import hu.bertokattila.pt.session.SessionDTO;
import hu.bertokattila.pt.session.data.SessionRepository;
import hu.bertokattila.pt.session.model.Session;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SessionService {
  private final SessionRepository repository;
  private final LocationService locationService;
  private final CurrencyExchangeService currencyExchangeService;
  private final KafkaTemplate<String, ExtendedSessionDTO> template;

  @Autowired
  public SessionService(SessionRepository sessionRepository, LocationService locationService, KafkaTemplate<String, ExtendedSessionDTO> template, CurrencyExchangeService currencyExchangeService) {
    repository = sessionRepository;
    this.locationService = locationService;
    this.template = template;
    this.currencyExchangeService = currencyExchangeService;
  }

  public Session saveSession(SessionDTO sessionDTO){
    if(sessionDTO.getEndDate().isBefore(sessionDTO.getStartDate())){
      return null;
    }
    Long locationId = null;
    if(sessionDTO.getLocation() != null){
      locationId = locationService.getLocationIdByName(sessionDTO.getLocation());
    }
    int id = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    Session session = new Session(sessionDTO, locationId, id);
    String defaultCurrency = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getDefaultCurrency();
    if(!defaultCurrency.equalsIgnoreCase(sessionDTO.getCurrency())){
      LocalDateTime date = session.getEndDate();
      Double[] buyInResult = currencyExchangeService.exchangeCurrency(sessionDTO.getCurrency().toUpperCase(), defaultCurrency.toUpperCase(), sessionDTO.getBuyIn(), date);
      Double[] cashOutResult = currencyExchangeService.exchangeCurrency(sessionDTO.getCurrency().toUpperCase(), defaultCurrency.toUpperCase(), sessionDTO.getCashOut(), date);
      if(buyInResult == null || cashOutResult == null){
        return null;
      }
      session.setBuyIn(buyInResult[0]);
      session.setCashOut(cashOutResult[0]);
      session.setExchangeRate(cashOutResult[1]);
    }else {
      session.setBuyIn(session.getBuyIn());
      session.setCashOut(session.getCashOut());
    }

    return repository.saveAndFlush(session);
  }

  public Optional<Session> getSession(int id){
    return repository.findById(id);
  }

  public void deleteSession(int id){
    repository.deleteById(id);
    int userId= ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
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
    session.setAccess(update.getAccess());
    repository.save(session);
    int userId= ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
  }

  public List<SessionDTO> getSessionsForLoggedInUser(GetSessionsDTO getSessionsDTO){
    int id = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    List<SessionRepository.sessionQuery> res = repository.findAllByUserId(id, getSessionsDTO.getLimit(), getSessionsDTO.getOffset());
    return convertSessionQuerytoSessionDto(res);
  }

  public List<ExtendedSessionDTO> getPublicSessionsOfUsers(PublicSessionsDTO dto){
    List<SessionRepository.sessionQuery> res = repository.findAllByUserIdsAndAccess(dto.getUserIds(), dto.getLimit(), dto.getOffset(), "public");
    return convertSessionQuerytoExtendedSessionDto(res);
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
      dto.setAccess(sessionQuery.getAccess());
      dto.setAnte(sessionQuery.getAnte());
      dto.setBlinds(sessionQuery.getBlinds());
      dto.setTableSize(sessionQuery.getTableSize());
      dto.setSpecificGameType(sessionQuery.getGame());
      dto.setExchangeRate(sessionQuery.getExchangeRate());
      sessionDTOs.add(dto);
    }
    return sessionDTOs;
  }

  private static List<ExtendedSessionDTO> convertSessionQuerytoExtendedSessionDto(List<SessionRepository.sessionQuery> res){
    List<ExtendedSessionDTO> sessionDTOs = new ArrayList<>();
    for(SessionRepository.sessionQuery sessionQuery : res){
      ExtendedSessionDTO dto = new ExtendedSessionDTO();
      dto.setId(sessionQuery.getId());
      dto.setUserId(sessionQuery.getUserId());
      dto.setBuyIn(sessionQuery.getBuyIn());
      dto.setCashOut(sessionQuery.getCashOut());
      dto.setComment(sessionQuery.getComment());
      dto.setCurrency(sessionQuery.getCurrency());
      dto.setEndDate(sessionQuery.getEndDate());
      dto.setLocation(sessionQuery.getLocation());
      dto.setStartDate(sessionQuery.getStartDate());
      dto.setType(sessionQuery.getType());
      dto.setAccess(sessionQuery.getAccess());
      dto.setAnte(sessionQuery.getAnte());
      dto.setBlinds(sessionQuery.getBlinds());
      dto.setTableSize(sessionQuery.getTableSize());
      dto.setSpecificGameType(sessionQuery.getGame());
      dto.setExchangeRate(sessionQuery.getExchangeRate());
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
      dto.setAccess(sessionQuery.getAccess());
      sessionDTOs.add(dto);
    }
    return sessionDTOs.toArray(new SessionDTO[0]);
  }
  public ExtendedSessionDTO[] getSessionsForUser(int userID){
    List<SessionRepository.sessionQuery> res = repository.findAllByUserId(userID);
    return convertSessionQuerytoExtendedSessionDto(res).toArray(new ExtendedSessionDTO[0]);
  }
  public void refreshStatistics(Session session){
    template.send("sessionReport", session.toExtendedSessionDTO());
  }

}
