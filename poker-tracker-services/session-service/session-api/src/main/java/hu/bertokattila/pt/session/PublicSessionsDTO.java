package hu.bertokattila.pt.session;

import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Data
public class PublicSessionsDTO {

  private List<Integer> userIds;
  @PositiveOrZero
  private int offset;
  @Positive
  private int limit;

  public PublicSessionsDTO(List<Integer> userIds, int offset, int limit) {
    this.userIds = userIds;
    this.offset = offset;
    this.limit = limit;
  }
}
