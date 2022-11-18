import { Component, OnInit } from '@angular/core';
import { RegisterService } from 'src/app/services/register.service';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  hide = true;
  hideRepeat = true;
  email: string = '';
  name: string = '';
  passw: string = '';
  rePassw: string = '';
  defCurr: string = '';
  constructor(
    public registerService: RegisterService,
    public dialog: MatDialog
  ) {}

  register() {
    if (this.passw != this.rePassw) {
      this.openDialog('An error occured', 'The given passwords do not match');
      return;
    }
    this.registerService
      .register(this.email, this.name, this.passw, this.defCurr)
      .subscribe({
        next: (v) => {
          this.openDialog('Success', 'New user has beeen successfully created');
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

  ngOnInit(): void {}
}
