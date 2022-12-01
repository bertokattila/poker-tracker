import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Session } from '../model/session';
import { serviceUrls } from './serviceUrls';

@Injectable({
  providedIn: 'root',
})
export class SessionService {
  constructor(private http: HttpClient) {}

  getSessions(limit: number, offset: number): Observable<Session[]> {
    return this.http.get<Session[]>(
      serviceUrls.sessionServiceUrl +
        '/sessions?limit=' +
        limit +
        '&offset=' +
        offset
    );
  }
  deleteSession(id: number) {
    return this.http.delete(serviceUrls.sessionServiceUrl + '/session/' + id);
  }
}
