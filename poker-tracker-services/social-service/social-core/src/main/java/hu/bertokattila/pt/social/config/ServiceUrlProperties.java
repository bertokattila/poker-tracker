package hu.bertokattila.pt.social.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("services")
public class ServiceUrlProperties {
  public String getUserServiceUrl() {
    return userServiceUrl;
  }

  public void setUserServiceUrl(String userServiceUrl) {
    this.userServiceUrl = userServiceUrl;
  }

  private String userServiceUrl;

  public String getSessionServiceUrl() {
    return sessionServiceUrl;
  }

  public void setSessionServiceUrl(String sessionServiceUrl) {
    this.sessionServiceUrl = sessionServiceUrl;
  }

  private String sessionServiceUrl;

}
