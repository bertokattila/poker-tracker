package hu.bertokattila.pt.user;

import lombok.Data;

@Data
public class LoginResponseDTO {
  private String jwt;
  public LoginResponseDTO(String jwt){
    this.jwt = jwt;
  }
}
