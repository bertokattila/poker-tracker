package hu.bertokattila.pt.user;

import lombok.Data;

@Data
public class LoginResponseDTO {
  private String jwt;
  private String userName;
  private String defaultCurrency;

  public LoginResponseDTO(String jwt, String userName, String defaultCurrency) {
    this.jwt = jwt;
    this.userName = userName;
    this.defaultCurrency = defaultCurrency;
  }
}
