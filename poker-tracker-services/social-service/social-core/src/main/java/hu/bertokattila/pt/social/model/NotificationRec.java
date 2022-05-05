package hu.bertokattila.pt.social.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "NotificationRec")
@Table(name = "notification")
public class NotificationRec {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "userid")
  private int userId;

  @Column(name = "sessionid")
  private int sessionId;

  @Column(name = "seen")
  private boolean seen;

}
