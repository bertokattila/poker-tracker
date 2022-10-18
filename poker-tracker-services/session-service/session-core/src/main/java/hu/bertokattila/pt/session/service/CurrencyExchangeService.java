package hu.bertokattila.pt.session.service;

import hu.bertokattila.pt.session.CurrencyExchangeResponse;
import hu.bertokattila.pt.session.config.ServiceUrlProperties;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyExchangeService {
  ServiceUrlProperties serviceUrlProperties;

  @Autowired
  public CurrencyExchangeService(ServiceUrlProperties serviceUrlProperties){
    this.serviceUrlProperties = serviceUrlProperties;
  }
  public Double[] exchangeCurrency(String from, String to, double amount, LocalDateTime date){
    RestTemplate restTemplate = new RestTemplate();
    String url = serviceUrlProperties.getExchangeServiceUrl();
    String token = serviceUrlProperties.getExchangeServiceToken();
    ResponseEntity<CurrencyExchangeResponse> response = null;
    MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
    headers.add("Content-Type", "application/json");
    headers.add("apikey", token);
    String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    try {
      response = restTemplate.exchange(
              url + "?to=" + to + "&from=" + from + "&amount=" + amount + "&date=" + dateStr, HttpMethod.GET, new HttpEntity<Object>(headers),
              CurrencyExchangeResponse.class);
    }catch (HttpClientErrorException e){
      if(e.getStatusCode().equals(HttpStatus.NOT_FOUND)){
        return null;
      }
    }
    if(response == null){
      return null;
    }
    CurrencyExchangeResponse resp = response.getBody();
    if(resp != null && resp.getSuccess()) {
      return new Double[]{resp.getResult(), resp.getInfo().getRate()};
    }
    return null;
  }
}
