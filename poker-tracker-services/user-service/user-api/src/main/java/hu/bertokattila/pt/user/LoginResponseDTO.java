package hu.bertokattila.pt.user;

import lombok.Data;

@Data
public class LoginResponseDTO {
  private String jwt;
  private String userName;
  public LoginResponseDTO(String jwt, String userName){
    this.jwt = jwt;
    this.userName = userName;
  }
}
