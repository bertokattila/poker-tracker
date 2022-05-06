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

  openDialog() {
    this.dialog.open(AddFriendDialogComponent, {});
  }

  ngOnInit(): void {}
}
