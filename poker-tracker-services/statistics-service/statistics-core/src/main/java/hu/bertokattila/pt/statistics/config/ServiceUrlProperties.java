package hu.bertokattila.pt.statistics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("services")
public class ServiceUrlProperties {
  public String getSessionServiceUrl() {
    return sessionServiceUrl;
  }

  public void setSessionServiceUrl(String sessionServiceUrl) {
    this.sessionServiceUrl = sessionServiceUrl;
  }

  private String sessionServiceUrl;
}
