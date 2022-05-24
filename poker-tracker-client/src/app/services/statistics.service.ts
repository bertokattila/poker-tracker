import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GenericStatDTO } from '../model/genericStatDto';
import { serviceUrls } from './serviceUrls';

@Injectable({
  providedIn: 'root',
})
export class StatisticsService {
  constructor(private http: HttpClient) {}

  getGenericStats(): Observable<GenericStatDTO[]> {
    return this.http.get<GenericStatDTO[]>(
      serviceUrls.statisticsServiceUrl + '/statistics/generic/'
    );
  }
  getYearlyResults(): Observable<any> {
    return this.http.get<any>(
      serviceUrls.statisticsServiceUrl + '/statistics/result/yearly'
    );
  }
  getMonthlyResults(): Observable<any> {
    return this.http.get<any>(
      serviceUrls.statisticsServiceUrl + '/statistics/result/monthly'
    );
  }
}
