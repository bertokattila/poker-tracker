import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { JwtDTO } from 'src/app/model/jwt';
import { LoginService } from 'src/app/services/login.service';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  constructor(public loginService: LoginService, public dialog: MatDialog) {}

  isLoggedIn: boolean;
  hide = true;
  email: string = '';
  passwd: string = '';

  login() {
    this.loginService.login(this.email, this.passwd).subscribe({
      next: (resp: JwtDTO) => {
        this.loginService.saveToken(resp.jwt);
      },
      error: (e) => {
        if (e.error.status === 400) {
          this.openDialog(
            'An error occured',
            'Validating the form data was not successful'
          );
        } else if (e.error.status === 500) {
          this.openDialog(
            'An error occured',
            'Probably a user exists with the given e-mail address or other server side error'
          );
        } else if (e.error.status === 403) {
          this.openDialog(
            'Login not successful',
            'Login credentials are invalid'
          );
        } else {
          this.openDialog('An error occured', 'Unknown error');
        }
      },
      complete: () => console.info('complete'),
    });
  }
  openDialog(title: string, desc: string) {
    this.dialog.open(DialogComponent, {
      data: {
        title: title,
        description: desc,
      },
    });
  }

  ngOnInit(): void {
    this.isLoggedIn = this.loginService.isLoggedIn();
    this.loginService.changeOccured$.subscribe((isLoggedIn: boolean) => {
      this.isLoggedIn = isLoggedIn;
    });
  }
}
