package hu.bertokattila.pt.social.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "SocialConnectionRec")
@Table(name = "social")
public class SocialConnectionRec {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "masteruserid")
  private int masterUserId;

  @Column(name = "slaveuserid")
  private int slaveUserId;

  @Column(name = "active")
  private boolean active;
}
