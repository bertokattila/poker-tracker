import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css'],
})
export class MainPageComponent implements OnInit {
  @Output() pageSelected = new EventEmitter<string>();
  constructor(public loginService: LoginService) {}
  isLoggedIn: boolean;
  selectedTabIndex: number;

  ngOnInit(): void {
    this.isLoggedIn = this.loginService.isLoggedIn();
    this.loginService.changeOccured$.subscribe((isLoggedIn: boolean) => {
      this.isLoggedIn = isLoggedIn;
    });
  }
  aleret(e: any) {
    if (e.index == 0) {
      this.pageSelected.emit('history');
    } else if (e.index == 2) {
      this.pageSelected.emit('statistics');
    }
  }
}
