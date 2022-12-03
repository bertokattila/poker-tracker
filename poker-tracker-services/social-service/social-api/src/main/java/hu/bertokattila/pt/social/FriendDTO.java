package hu.bertokattila.pt.social;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FriendDTO {

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String name;

  // id of the social connection
  private int socialId;
}
