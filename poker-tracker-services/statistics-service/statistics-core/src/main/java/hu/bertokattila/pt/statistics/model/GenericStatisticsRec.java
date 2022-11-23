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

  public GenericStatisticsRec(){
    this.allTimeResult = 0;
    this.allTimePlayedTime = 0;
    this.lastMonthResult = 0;
    this.lastMonthPlayedTime = 0;
    this.lastYearResult = 0;
    this.lastYearPlayedTime = 0;
  }

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
  private int lastMonthPlayedTime;

  @Column(name = "lastyearplayedtime")
  private int lastYearPlayedTime;

  @Column(name = "alltimeplayedtime")
  private int allTimePlayedTime;

  @Column(name = "numberofcashgames")
  private int numberOfCashGames;

  @Column(name = "numberoftournaments")
  private int numberOfTournaments;

  @Column(name = "numberoftablesize2")
  private Integer numberOfTableSize2;

  @Column(name = "numberoftablesize3")
  private Integer numberOfTableSize3;

  @Column(name = "numberoftablesize4")
  private Integer numberOfTableSize4;

  @Column(name = "numberoftablesize5")
  private Integer numberOfTableSize5;

  @Column(name = "numberoftablesize6")
  private Integer numberOfTableSize6;

  @Column(name = "numberoftablesize7")
  private Integer numberOfTableSize7;

  @Column(name = "numberoftablesize8")
  private Integer numberOfTableSize8;

  @Column(name = "numberoftablesize9")
  private Integer numberOfTableSize9;

  @Column(name = "numberoftablesize10")
  private Integer numberOfTableSize10;

}
