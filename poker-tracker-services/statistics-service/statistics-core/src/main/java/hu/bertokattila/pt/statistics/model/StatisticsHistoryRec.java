package hu.bertokattila.pt.statistics.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "StatisticsHistoryRec")
@Table(name = "statistics_history")
public class StatisticsHistoryRec {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "userid")
  private int userId;
  @Column(name = "sessionid")
  private int sessionId;
  @Column(name = "startdate")
  private LocalDateTime startDate;
  @Column(name = "enddate")
  private LocalDateTime endDate;
  @Column(name = "result")
  private double result;
  @Column(name = "playedtime")
  private int playedTime;
  @Column(name = "type")
  private String type;

  public StatisticsHistoryRec() {
  }
}
