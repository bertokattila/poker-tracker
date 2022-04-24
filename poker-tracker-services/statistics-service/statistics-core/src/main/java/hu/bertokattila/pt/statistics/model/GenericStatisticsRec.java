package hu.bertokattila.pt.statistics.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity(name = "GenericStatisticsRec")
@Table(name = "generic_statistics")
public class GenericStatisticsRec {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "userid")
  private int userId;

  @Column(name = "lastmonthresult")
  private double lastMonthResult;

  @Column(name = "lastyearresult")
  private double lastYearResult;

  @Column(name = "alltimeresult")
  private double allTimeResult;

  @Column(name = "lastmonthplayedtime")
  private long lastMonthPlayedTime;

  @Column(name = "lastyearplayedtime")
  private long lastYearPlayedTime;

  @Column(name = "alltimeplayedtime")
  private long allTimePlayedTime;
}
