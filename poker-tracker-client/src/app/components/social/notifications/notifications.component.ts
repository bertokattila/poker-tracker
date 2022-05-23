import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Session } from 'src/app/model/session';
import { SessionWithOwnerDTO } from 'src/app/model/sessionWithOwnerDTO';
import { SocialService } from 'src/app/services/social-service.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css'],
})
export class NotificationsComponent implements OnInit {
  constructor(private socialService: SocialService, public datepipe: DatePipe) {
    this.limit = 10;
    this.offset = 0;
    this.canLoadMore = true;
    this.fetchMoreSessions();
  }
  notifications: SessionWithOwnerDTO[] = [];
  limit: number;
  offset: number;
  canLoadMore: boolean;
  public myMath = Math;

  fetchMoreSessions = () => {
    this.socialService.getNotifications(this.limit, this.offset).subscribe({
      next: (sessions) => {
        this.notifications = this.notifications.concat(sessions);
        if (sessions.length < this.limit) {
          this.canLoadMore = false;
        }
        console.log(this.notifications);
      },
      error: (e) => {},
    });
  };
  loadMore() {
    this.offset += this.limit;
    this.fetchMoreSessions();
  }
  ngOnInit(): void {}
}
