package hu.bertokattila.pt.social;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class AddFriendDTO {

  @NotBlank
  @Email
  private String email;
}
