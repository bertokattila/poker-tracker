import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { GameType, AccessType } from 'src/app/model/addSessionDTO';
import { AddSessionService } from 'src/app/services/add-session.service';
import { CurrencyService } from 'src/app/services/currency.service';
import { LoginService } from 'src/app/services/login.service';
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
  specificGameType: string = "Hold'em";
  ante: number | undefined;
  blinds: number | undefined;
  tableSize: number | undefined;

  defaultyCurrency: string;

  buyInDefaultCurrency: number;
  cashOutDefaultCurrency: number;

  public myMath = Math;

  constructor(
    private addSessionService: AddSessionService,
    private dialog: MatDialog,
    private loginService: LoginService,
    private currencyService: CurrencyService
  ) {
    this.startDate = new Date(new Date().getTime() - 60000 * 60)
      .toISOString()
      .slice(0, 16);
    this.endDate = new Date().toISOString().slice(0, 16);

    this.defaultyCurrency = loginService.getDefaultCurrency() || '';
    this.currency = this.defaultyCurrency || 'huf';
  }
  addSession() {
    if (this.gameType === 'tournament') {
      this.ante = undefined;
      this.blinds = undefined;
    }
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
        this.accessType,
        this.specificGameType,
        this.ante,
        this.blinds,
        this.tableSize
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
  onBuyInChanges() {
    if (this.defaultyCurrency != this.currency && this.buyIn) {
      this.currencyService
        .exchangeCurrency(
          this.currency,
          this.defaultyCurrency,
          this.buyIn,
          this.endDate
        )
        .subscribe({
          next: (resp) => {
            this.buyInDefaultCurrency = resp.result;
          },
          error: (e) => {
            this.openDialog(
              'An error occured',
              'Exchanging currency was not successful'
            );
          },
        });
    }
  }
  onCashOutChanges() {
    if (this.defaultyCurrency != this.currency && this.cashOut) {
      this.currencyService
        .exchangeCurrency(
          this.currency,
          this.defaultyCurrency,
          this.cashOut,
          this.endDate
        )
        .subscribe({
          next: (resp) => {
            this.cashOutDefaultCurrency = resp.result;
          },
          error: (e) => {
            this.openDialog(
              'An error occured',
              'Exchanging currency was not successful'
            );
          },
        });
    }
  }
  reLoadCurrencyExchange() {
    this.onBuyInChanges();
    this.onCashOutChanges();
  }

  ngOnInit(): void {}
}
