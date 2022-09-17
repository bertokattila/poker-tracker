import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AccessType, AddsessionDTO, GameType } from '../model/addSessionDTO';
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
    location: string,
    accessType: AccessType
  ) {
    const dto = new AddsessionDTO(
      type,
      currency,
      buyIn,
      cashOut,
      startDate,
      endDate,
      comment,
      location,
      accessType
    );

    return this.http.post<AddsessionDTO>(
      serviceUrls.sessionServiceUrl + '/session',
      dto
    );
  }
}
