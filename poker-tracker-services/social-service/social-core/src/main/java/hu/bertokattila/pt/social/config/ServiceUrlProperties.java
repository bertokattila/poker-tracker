package hu.bertokattila.pt.social.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("services")
public class ServiceUrlProperties {
  private String userServiceUrl;
  private String sessionServiceUrl;

  public String getUserServiceUrl() {
    return userServiceUrl;
  }

  public void setUserServiceUrl(String userServiceUrl) {
    this.userServiceUrl = userServiceUrl;
  }

  public String getSessionServiceUrl() {
    return sessionServiceUrl;
  }

  public void setSessionServiceUrl(String sessionServiceUrl) {
    this.sessionServiceUrl = sessionServiceUrl;
  }

}
