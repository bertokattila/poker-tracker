package hu.bertokattila.pt.session;

import lombok.Data;

@Data
public class CurrencyExchangeResponse {

  Boolean success;
  Double result;
  Info info;

  public static class Info {
    double rate;
    public double getRate() {
      return rate;
    }
  }
}
