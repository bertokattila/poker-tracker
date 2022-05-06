import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../../dialog/dialog.component';
import { AddFriendDialogComponent } from '../add-friend-dialog/add-friend-dialog.component';

@Component({
  selector: 'app-add-friend',
  templateUrl: './add-friend.component.html',
  styleUrls: ['./add-friend.component.css'],
})
export class AddFriendComponent implements OnInit {
  email: string;
  constructor(private dialog: MatDialog) {}

  openDialog(title: string, desc: string) {
    this.dialog.open(AddFriendDialogComponent, {
      data: {
        title: title,
        description: desc,
      },
    });
  }

  ngOnInit(): void {}
}
