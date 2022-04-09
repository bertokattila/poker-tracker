import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegisterDTO } from '../model/registerDTO';
import { serviceUrls } from './serviceUrls';

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  constructor(private http: HttpClient) {}

  register(
    email: string,
    name: string,
    password: string,
    defaultCurrency: string
  ) {
    const dto = new RegisterDTO(email, name, password, defaultCurrency);

    return this.http.post<RegisterDTO>(
      serviceUrls.userServiceUrl + '/register',
      dto
    );
  }
}
