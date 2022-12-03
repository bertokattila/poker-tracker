package hu.bertokattila.pt.session;

import java.util.List;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;

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
