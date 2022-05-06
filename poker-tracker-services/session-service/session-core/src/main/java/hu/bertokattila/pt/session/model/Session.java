package hu.bertokattila.pt.session.model;

import hu.bertokattila.pt.session.SessionDTO;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity(name = "session")
@Table(name = "session")
public class Session {

  public Session() {}
  public Session(SessionDTO sessionDTO, Long locationId, int userId ) {
    this.userId = userId;
    this.type = sessionDTO.getType();
    this.currency = sessionDTO.getCurrency();
    this.buyIn = sessionDTO.getBuyIn();
    this.cashOut = sessionDTO.getCashOut();
    this.startDate = sessionDTO.getStartDate();
    this.endDate = sessionDTO.getEndDate();
    this.comment = sessionDTO.getComment();
    this.locationId = locationId;
    this.access = sessionDTO.getAccess();
  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "userid")
  private int userId;

  @Column(name = "type")
  private String type;

  @Column(name = "currency")
  private String currency;

  @Column(name = "buyin")
  private double buyIn;

  @Column(name = "cashout")
  private double cashOut;

  @Column(name = "startdate")
  private LocalDateTime startDate;

  @Column(name = "enddate")
  private LocalDateTime endDate;

  @Column(name = "comment")
  private String comment;

  @Column(name = "locationid")
  private Long locationId;

  @Column(name = "access")
  private String access;

}
