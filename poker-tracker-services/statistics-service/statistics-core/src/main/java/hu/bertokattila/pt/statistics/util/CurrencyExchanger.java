package hu.bertokattila.pt.statistics.util;

public class CurrencyExchanger {
  public static double exchange(String from, String to, double amount){
    //TODO elo adatok
    if(from.equals("HUF") && to.equals("EUR")){
      return amount * 400;
    }
    if(from.equals("EUR") && to.equals("HUF")){
      return amount / 400.0;
    }
    return 0;
  }

}
