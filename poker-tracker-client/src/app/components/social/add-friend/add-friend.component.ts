import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { FriendDTO } from 'src/app/model/friendDTO';
import { SocialService } from 'src/app/services/social-service.service';
import { DialogComponent } from '../../dialog/dialog.component';
import { AddFriendDialogComponent } from '../add-friend-dialog/add-friend-dialog.component';

@Component({
  selector: 'app-add-friend',
  templateUrl: './add-friend.component.html',
  styleUrls: ['./add-friend.component.css'],
})
export class AddFriendComponent implements OnInit {
  email: string;
  friends: FriendDTO[] = [];
  addedFriends: FriendDTO[] = [];
  friendRequests: FriendDTO[] = [];
  constructor(private dialog: MatDialog, private socialService: SocialService) {
    this.fetchFriends();
    this.fetchAddedFriends();
    this.fetchFriendRequests();
  }

  openDialog() {
    this.dialog.open(AddFriendDialogComponent, {});
  }

  fetchFriends = () => {
    this.socialService.getFriends().subscribe({
      next: (friends) => {
        if (friends != null) {
          this.friends = friends as FriendDTO[];
        }
        //console.log(this.friends);
      },
    });
  };

  fetchAddedFriends = () => {
    this.socialService.getAddedFriends().subscribe({
      next: (friends) => {
        if (friends != null) {
          this.addedFriends = friends as FriendDTO[];
        } else {
          this.addedFriends = [];
        }
        //console.log(this.addedFriends);
      },
    });
  };

  fetchFriendRequests = () => {
    this.socialService.getFriendRequests().subscribe({
      next: (friends) => {
        if (friends != null) {
          this.friendRequests = friends as FriendDTO[];
        } else {
          this.friendRequests = [];
        }
        //console.log(this.friendRequests);
      },
    });
  };

  acceptFriendRequest(id: number) {
    this.socialService.acceptFriendRequest(id).subscribe({
      next: (friend) => {
        this.fetchAddedFriends();
        this.fetchFriends();
        this.fetchFriendRequests();
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  ngOnInit(): void {}
}
