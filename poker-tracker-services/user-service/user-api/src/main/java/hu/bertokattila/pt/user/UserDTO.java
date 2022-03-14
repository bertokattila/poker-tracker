package hu.bertokattila.pt.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserDTO {

  @NotBlank
  private String name;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String password;

  @NotBlank
  private String defaultCurrency;
}
