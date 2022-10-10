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

  public String getExchangeServiceUrl() {
    return exchangeServiceUrl;
  }

  public void setExchangeServiceUrl(String exchangeServiceUrl) {
    this.exchangeServiceUrl = exchangeServiceUrl;
  }

  private String exchangeServiceUrl;

  public String getExchangeServiceToken() {
    return exchangeServiceToken;
  }

  public void setExchangeServiceToken(String exchangeServiceToken) {
    this.exchangeServiceToken = exchangeServiceToken;
  }

  private String exchangeServiceToken;
}
