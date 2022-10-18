import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CurrencyExchangeDTO } from '../model/currencyExchangeDTO';
import { serviceUrls } from './serviceUrls';

@Injectable({
  providedIn: 'root',
})
export class CurrencyService {
  constructor(private http: HttpClient) {}
  exchangeCurrency(
    from: string,
    to: string,
    amount: number,
    date: string
  ): Observable<CurrencyExchangeDTO> {
    return this.http.get<CurrencyExchangeDTO>(
      serviceUrls.sessionServiceUrl +
        '/currency/exchange?from=' +
        from +
        '&to=' +
        to +
        '&amount=' +
        amount +
        '&date=' +
        date
    );
  }
}
