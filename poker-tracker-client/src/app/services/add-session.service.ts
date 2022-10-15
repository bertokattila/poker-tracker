import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AccessType, AddSessionDTO, GameType } from '../model/addSessionDTO';
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
    accessType: AccessType,
    specificGameType: string,
    ante: number | undefined,
    blinds: number | undefined,
    tableSize: number | undefined
  ) {
    const dto = new AddSessionDTO(
      type,
      currency,
      buyIn,
      cashOut,
      startDate,
      endDate,
      comment,
      location,
      accessType,
      specificGameType,
      ante,
      blinds,
      tableSize
    );

    return this.http.post<AddSessionDTO>(
      serviceUrls.sessionServiceUrl + '/session',
      dto
    );
  }
}
