import { Component } from '@angular/core';
import { LoginService } from './services/login.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  constructor(public loginservice: LoginService) {}
  isLoggedIn: boolean;
  ngOnInit(): void {
    this.isLoggedIn = this.loginservice.isLoggedIn();
    this.loginservice.changeOccured$.subscribe((isLoggedIn: boolean) => {
      this.isLoggedIn = isLoggedIn;
    });
  }

  title = 'poker-tracker-client';
}
