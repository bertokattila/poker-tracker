import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css'],
})
export class MainPageComponent implements OnInit {
  constructor(public loginService: LoginService) {}
  isLoggedIn: boolean;
  selectedTabIndex: number;

  ngOnInit(): void {
    this.isLoggedIn = this.loginService.isLoggedIn();
    this.loginService.changeOccured$.subscribe((isLoggedIn: boolean) => {
      this.isLoggedIn = isLoggedIn;
    });
  }
}
