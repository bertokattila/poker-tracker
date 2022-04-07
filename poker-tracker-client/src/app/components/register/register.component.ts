import { Component, OnInit } from '@angular/core';
import { RegisterService } from 'src/app/services/register.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  hide = true;
  hideRepeat = true;
  email: string = '';
  constructor(public registerService: RegisterService) {}

  register() {
    this.registerService.register(this.email, 'sdad', 'sdsad', 'dsa');
  }
  ngOnInit(): void {}
}
