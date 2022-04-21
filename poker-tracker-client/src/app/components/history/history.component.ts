import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Session } from 'src/app/model/session';
import { SessionService } from 'src/app/services/session.service';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css'],
})
export class HistoryComponent implements OnInit {
  constructor(
    private sessionService: SessionService,
    private dialog: MatDialog,
    public datepipe: DatePipe
  ) {
    this.limit = 10;
    this.offset = 0;
    this.canLoadMore = true;
    this.fetchMoreSessions();
  }
  limit: number;
  offset: number;
  sessions: Session[] = [];
  canLoadMore: boolean;
  public myMath = Math;

  fetchMoreSessions = () => {
    this.sessionService.getSessions(this.limit, this.offset).subscribe({
      next: (sessions) => {
        this.sessions = this.sessions.concat(sessions);
        if (sessions.length < this.limit) {
          this.canLoadMore = false;
        }
      },
      error: (e) => {
        if (e.error.status === 400) {
          this.openDialog('An error occured', 'Unknown error');
        }
      },
    });
  };
  openDialog(title: string, desc: string) {
    this.dialog.open(DialogComponent, {
      data: {
        title: title,
        description: desc,
      },
    });
  }
  loadMore() {
    this.offset += this.limit;
    this.fetchMoreSessions();
  }

  ngOnInit(): void {}
}
