package hu.bertokattila.pt.session.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("services")
public class ServiceUrlProperties {
  public String getStatisticsServiceUrl() {
    return statisticsServiceUrl;
  }

  public void setStatisticsServiceUrl(String statisticsServiceUrl) {
    this.statisticsServiceUrl = statisticsServiceUrl;
  }

  private String statisticsServiceUrl;
}
