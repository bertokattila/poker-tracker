package hu.bertokattila.pt.social;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddFriendDTO {

  @NotBlank
  @Email
  private String email;
}
