import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { JwtDTO } from '../model/jwt';
import { LoginDTO } from '../model/loginDTO';
import { serviceUrls } from './serviceUrls';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  constructor(private http: HttpClient) {}

  login(email: string, password: string) {
    const dto = new LoginDTO(email, password);
    return this.http.post<JwtDTO>(serviceUrls.userServiceUrl + '/login', dto);
  }
  saveToken(token: string) {
    localStorage.setItem('poker_tracker_token', token);
    this.callChangeOccured();
  }
  logout() {
    localStorage.removeItem('poker_tracker_token');
    this.callChangeOccured();
  }
  public isLoggedIn() {
    return localStorage.getItem('poker_tracker_token') !== null;
  }
  public getUsername() {
    return localStorage.getItem('poker_tracker_token') !== null
      ? localStorage.getItem('poker_tracker_token')
      : '';
  }

  private changeOccured = new Subject<any>();

  changeOccured$ = this.changeOccured.asObservable();

  callChangeOccured() {
    this.changeOccured.next(this.isLoggedIn());
  }
}
