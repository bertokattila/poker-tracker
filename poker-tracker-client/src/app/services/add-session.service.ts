import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AddsessionDTO, GameType } from '../model/addSessionDto';
import { serviceUrls } from './serviceUrls';

@Injectable({
  providedIn: 'root',
})
export class AddSessionService {
  constructor(private http: HttpClient) {}

  addSession(
    type: GameType,
    currency: string,
    buyIn: number,
    cashOut: number,
    startDate: string,
    endDate: string,
    comment: string,
    location: string
  ) {
    const dto = new AddsessionDTO(
      type,
      currency,
      buyIn,
      cashOut,
      startDate,
      endDate,
      comment,
      location
    );

    return this.http.post<AddsessionDTO>(
      serviceUrls.sessionServiceUrl + '/session',
      dto
    );
  }
}
