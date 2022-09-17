import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { GameType, AccessType } from 'src/app/model/addSessionDTO';
import { AddSessionService } from 'src/app/services/add-session.service';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-add-session',
  templateUrl: './add-session.component.html',
  styleUrls: ['./add-session.component.css'],
})
export class AddSessionComponent implements OnInit {
  startDate: string;
  endDate: string;
  gameType: GameType = GameType.cash;
  accessType: AccessType = AccessType.public;
  setGameType(type: string) {
    this.gameType = type as GameType;
  }
  setAccessType(type: string) {
    this.accessType = type as AccessType;
  }
  currency: string;
  buyIn: number;
  cashOut: number;
  comment: string;
  location: string;

  constructor(
    private addSessionService: AddSessionService,
    private dialog: MatDialog
  ) {
    this.startDate = new Date(new Date().getTime() - 60000 * 60)
      .toISOString()
      .slice(0, 16);
    this.endDate = new Date().toISOString().slice(0, 16);
    this.currency = 'huf';
  }
  addSession() {
    this.addSessionService
      .addSession(
        this.gameType,
        this.currency,
        this.buyIn,
        this.cashOut,
        this.startDate,
        this.endDate,
        this.comment,
        this.location,
        this.accessType
      )
      .subscribe({
        next: (resp) => {
          this.openDialog('Congratulations', 'Session saved successfully ');
        },
        error: (e) => {
          if (e.error.status === 400) {
            this.openDialog(
              'An error occured',
              'Validating the form data was not successful'
            );
          } else {
            this.openDialog('An error occured', 'Unknown error');
          }
        },
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
