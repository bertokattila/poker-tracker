package hu.bertokattila.pt.session;

import javax.validation.constraints.Positive;
import lombok.Data;

@Data
public class SessionRemovedDTO {
  @Positive
  private Integer sessionId;
  private Integer tableSize;
}
