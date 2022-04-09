import { Component, HostBinding, Input, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  @Input()
  isLoggedIn: boolean;

  userName: string | null;
  constructor(public loginservice: LoginService) {}

  ngOnInit(): void {
    if (this.isLoggedIn) {
      this.userName = this.loginservice.getUsername();
    }
  }
  ngOnChanges() {
    if (this.isLoggedIn) {
      this.userName = this.loginservice.getUsername();
    } else {
      this.userName = '';
    }
  }
  logOut() {
    this.loginservice.logout();
  }
}
