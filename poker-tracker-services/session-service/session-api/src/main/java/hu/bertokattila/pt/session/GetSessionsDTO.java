package hu.bertokattila.pt.session;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class GetSessionsDTO {
  public GetSessionsDTO(int limit, int offset) {
    this.limit = limit;
    this.offset = offset;
  }
  @Positive
  private int limit;
  @PositiveOrZero
  private int offset;
}
