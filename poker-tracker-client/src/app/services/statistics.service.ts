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
}
