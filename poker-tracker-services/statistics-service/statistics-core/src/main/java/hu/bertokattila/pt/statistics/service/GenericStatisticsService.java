package hu.bertokattila.pt.statistics.service;


import hu.bertokattila.pt.auth.AuthUser;
import hu.bertokattila.pt.session.SessionDTO;
import hu.bertokattila.pt.statistics.config.ServiceUrlProperties;
import hu.bertokattila.pt.statistics.data.GenericStatisticsRepository;
import hu.bertokattila.pt.statistics.model.GenericStatisticsRec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class GenericStatisticsService {
  private final GenericStatisticsRepository repository;
  private final ServiceUrlProperties serviceUrlProperties;
  @Autowired
  public GenericStatisticsService(GenericStatisticsRepository repository, ServiceUrlProperties serviceUrlProperties){
    this.repository = repository;
    this.serviceUrlProperties = serviceUrlProperties;
  }
  public void refreshStatistics(int userID){
    RestTemplate restTemplate = new RestTemplate();
    String url = serviceUrlProperties.getSessionServiceUrl();
    ResponseEntity<SessionDTO[]> response
            = restTemplate.getForEntity(url + "/internal/sessions/" + userID, SessionDTO[].class);
    SessionDTO[] sessions = response.getBody();

    GenericStatisticsRec rec = repository.findByUserId(userID).orElse(null);
    if(rec == null){
      rec = new GenericStatisticsRec();
      rec.setUserId(userID);
      calculateStatistics(sessions, rec);
    }else{
      calculateStatistics(sessions, rec);
    }
    repository.save(rec);
  }

  /**
   * Calculating aggregated statistics for a user
   * @param sessions sessions of a user
   * @param rec statistics record
   */
  private void calculateStatistics(SessionDTO[] sessions, GenericStatisticsRec rec) {
    ZoneId z = ZoneId.of("Europe/Budapest");
    LocalDateTime now = ZonedDateTime.now(z).toLocalDateTime();
    List<SessionDTO> sessionDTOListAllTime = Arrays.stream(sessions).toList();
    List<SessionDTO> sessionDTOListLast30Days = sessionDTOListAllTime.stream().filter(s -> s.getStartDate().isAfter(now.minusDays(30))).toList();
    List<SessionDTO> sessionDTOListLastYear = sessionDTOListAllTime.stream().filter(s -> s.getStartDate().isAfter(now.minusDays(365))).toList();

    long minutesAllTime = 0;
    long resultAllTime = 0;
    for (SessionDTO session : sessionDTOListAllTime) {
      minutesAllTime += ChronoUnit.MINUTES.between(session.getStartDate(), session.getEndDate());
      resultAllTime += (session.getCashOut() - session.getBuyIn());
    }
    rec.setAllTimePlayedTime(minutesAllTime);
    rec.setAllTimeResult(resultAllTime);

    long minutesLastYear = 0;
    long resultLastYear = 0;
    for (SessionDTO session : sessionDTOListLastYear) {
      minutesLastYear += ChronoUnit.MINUTES.between(session.getStartDate(), session.getEndDate());
      resultLastYear += (session.getCashOut() - session.getBuyIn());
    }
    rec.setLastYearPlayedTime(minutesLastYear);
    rec.setLastYearResult(resultLastYear);

    long minutesLast30Days = 0;
    long resultLast30Days = 0;
    for (SessionDTO session : sessionDTOListLast30Days) {
      minutesLast30Days += ChronoUnit.MINUTES.between(session.getStartDate(), session.getEndDate());
      resultLast30Days += (session.getCashOut() - session.getBuyIn());
    }
    rec.setLastMonthPlayedTime(minutesLast30Days);
    rec.setLastMonthResult(resultLast30Days);
  }

  public GenericStatisticsRec getGenericStatistics() {
    int userID = ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    return repository.findByUserId(userID).orElse(null);
  }
}
