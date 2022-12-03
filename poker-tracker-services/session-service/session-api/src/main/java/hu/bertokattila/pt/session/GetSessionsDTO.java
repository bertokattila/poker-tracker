package hu.bertokattila.pt.session;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class GetSessionsDTO {
  @Positive
  private int limit;
  @PositiveOrZero
  private int offset;

  public GetSessionsDTO(int limit, int offset) {
    this.limit = limit;
    this.offset = offset;
  }
}
