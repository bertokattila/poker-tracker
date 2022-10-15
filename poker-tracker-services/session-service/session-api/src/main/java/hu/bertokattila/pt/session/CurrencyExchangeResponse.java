package hu.bertokattila.pt.session;

import lombok.Data;

@Data
public class CurrencyExchangeResponse {

  public static class Info {
    double rate;
    public double getRate() {
      return rate;
    }
  }
  Boolean success;
  Double result;
  Info info;

}
