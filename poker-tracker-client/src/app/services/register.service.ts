import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RegisterService {
  constructor() {}

  register(
    email: string,
    name: string,
    password: string,
    defaultCurrency: string
  ) {
    console.log(email);
    console.log(name);
    console.log(password);
    console.log(defaultCurrency);
  }
}
