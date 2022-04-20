package hu.bertokattila.pt.session;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class GetSessionsDTO {
  @Positive
  private int limit;
  @PositiveOrZero
  private int offset;
}
