package hu.bertokattila.pt.statistics.service;


import hu.bertokattila.pt.auth.AuthUser;
import hu.bertokattila.pt.session.ExtendedSessionDTO;
import hu.bertokattila.pt.session.SessionDTO;
import hu.bertokattila.pt.social.DataPointDTO;
import hu.bertokattila.pt.social.DataSeriesDTO;
import hu.bertokattila.pt.statistics.config.ServiceUrlProperties;
import hu.bertokattila.pt.statistics.data.GenericStatisticsRepository;
import hu.bertokattila.pt.statistics.data.StatisticsHistoryRepository;
import hu.bertokattila.pt.statistics.model.GenericStatisticsRec;
import hu.bertokattila.pt.statistics.model.StatisticsHistoryRec;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class GenericStatisticsService {
  private final GenericStatisticsRepository repository;
  private final StatisticsHistoryRepository statRepo;
  private final ServiceUrlProperties serviceUrlProperties;
  @Autowired
  public GenericStatisticsService(GenericStatisticsRepository repository,StatisticsHistoryRepository statisticsHistoryRepository , ServiceUrlProperties serviceUrlProperties){
    this.repository = repository;
    this.serviceUrlProperties = serviceUrlProperties;
    this.statRepo = statisticsHistoryRepository;
  }
  public void refreshStatistics(ExtendedSessionDTO session){
    //RestTemplate restTemplate = new RestTemplate();
    //String url = serviceUrlProperties.getSessionServiceUrl();
    //ResponseEntity<ExtendedSessionDTO[]> response
     //       = restTemplate.getForEntity(url + "/internal/sessions/" + userID, ExtendedSessionDTO[].class);
    //ExtendedSessionDTO session = response.getBody();

    GenericStatisticsRec rec = repository.findByUserId(session.getUserId()).orElse(null);
    if(rec == null){
      rec = new GenericStatisticsRec();
      rec.setUserId(session.getUserId());
      calculateStatistics(session, rec);
    }else{
      calculateStatistics(session, rec);
    }
    repository.save(rec);
    refreshStatHistory(session);
  }

  private void refreshStatHistory(ExtendedSessionDTO session){
      StatisticsHistoryRec rec = statRepo.getBySessionId(session.getId());
      if(rec == null){
        rec = new StatisticsHistoryRec();
        rec.setUserId(session.getUserId());
        rec.setSessionId(session.getId());
        rec.setStartDate(session.getStartDate());
        rec.setEndDate(session.getEndDate());
        rec.setResult(session.getCashOut() - session.getBuyIn());
        rec.setPlayedTime((int) ChronoUnit.MINUTES.between(session.getStartDate(), session.getEndDate()));
        rec.setType(session.getType());
        statRepo.save(rec);
      }
  }

  /**
   * Calculating aggregated statistics for a user when a session is added
   * @param session new session added
   * @param rec statistics record
   */
  private void calculateStatistics(ExtendedSessionDTO session, GenericStatisticsRec rec) {
    ZoneId z = ZoneId.of("Europe/Budapest");
    LocalDateTime now = ZonedDateTime.now(z).toLocalDateTime();

    long daysSinceSession = ChronoUnit.DAYS.between(session.getEndDate(), now);

    rec.setAllTimePlayedTime(rec.getAllTimePlayedTime() + ChronoUnit.MINUTES.between(session.getStartDate(), session.getEndDate()));
    rec.setAllTimeResult(rec.getAllTimeResult() + (session.getCashOut() - session.getBuyIn()));

    if(daysSinceSession <= 365) {
      rec.setLastYearPlayedTime(rec.getLastYearPlayedTime() + ChronoUnit.MINUTES.between(session.getStartDate(), session.getEndDate()));
      rec.setLastYearResult(rec.getLastYearResult() + session.getCashOut() - session.getBuyIn());
    }

    if(daysSinceSession <= 30) {
      rec.setLastMonthPlayedTime(rec.getLastMonthPlayedTime() + ChronoUnit.MINUTES.between(session.getStartDate(), session.getEndDate()));
      rec.setLastMonthResult(rec.getLastMonthResult() + (session.getCashOut() - session.getBuyIn()));
    }
  }

  public GenericStatisticsRec getGenericStatistics() {
    int userId= ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
    return repository.findByUserId(userId).orElse(null);
  }

  public List<DataSeriesDTO> getYearlyResult() {
    int userId = getUserId();
    List<StatisticsHistoryRepository.StatQuery> res = statRepo.getYearlyStats(userId);
    HashMap<String, DataSeriesDTO> map = new HashMap<>();
    for (StatisticsHistoryRepository.StatQuery q : res) {
      String key = q.getYear();
      if (!map.containsKey(key)) {
        DataSeriesDTO d = new DataSeriesDTO();
        d.setName(key);
        ArrayList<DataPointDTO> dataPoints = new ArrayList<>();
        DataPointDTO dpCash = new DataPointDTO();
        dpCash.setName("Cash Game");
        dpCash.setValue(0);
        DataPointDTO dpTournament = new DataPointDTO();
        dpTournament.setName("Tournament");
        dpTournament.setValue(0);
        dataPoints.add(dpCash);
        dataPoints.add(dpTournament);
        d.setSeries(dataPoints);
        map.put(key, d);
      }
      DataSeriesDTO d = map.get(key);
      if (q.getType().equals("cash")) {
        d.getSeries().get(0).setValue(q.getResult() + d.getSeries().get(0).getValue());
      }else {
        d.getSeries().get(1).setValue(q.getResult() + d.getSeries().get(1).getValue());
      }
    }
    return new ArrayList<DataSeriesDTO>(map.values());
  }

  public List<DataSeriesDTO> getMonthlyResult() {
    int userId = getUserId();
    List<StatisticsHistoryRepository.StatQueryMonthly> res = statRepo.getMonthlyStats(userId);
    HashMap<String, DataSeriesDTO> map = new HashMap<>();
    for (StatisticsHistoryRepository.StatQueryMonthly q : res) {
      String key = q.getMonth();
      if (!map.containsKey(key)) {
        DataSeriesDTO d = new DataSeriesDTO();
        d.setName(key.replaceAll("\\s+",""));
        ArrayList<DataPointDTO> dataPoints = new ArrayList<>();
        DataPointDTO dpCash = new DataPointDTO();
        dpCash.setName("Cash Game");
        dpCash.setValue(0);
        DataPointDTO dpTournament = new DataPointDTO();
        dpTournament.setName("Tournament");
        dpTournament.setValue(0);
        dataPoints.add(dpCash);
        dataPoints.add(dpTournament);
        d.setSeries(dataPoints);
        map.put(key, d);
      }
      DataSeriesDTO d = map.get(key);
      if (q.getType().equals("cash")) {
        d.getSeries().get(0).setValue(q.getResult() + d.getSeries().get(0).getValue());
      }else {
        d.getSeries().get(1).setValue(q.getResult() + d.getSeries().get(1).getValue());
      }
    }
    return new ArrayList<DataSeriesDTO>(map.values());
  }

  public int getUserId(){
    return ((AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
  }
}
