import { Component, Inject, OnInit } from '@angular/core';
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA,
} from '@angular/material/dialog';
import { SocialService } from 'src/app/services/social-service.service';
import { DialogComponent } from '../../dialog/dialog.component';

@Component({
  selector: 'app-add-friend-dialog',
  templateUrl: './add-friend-dialog.component.html',
  styleUrls: ['./add-friend-dialog.component.css'],
})
export class AddFriendDialogComponent implements OnInit {
  constructor(
    public dialogRef: MatDialogRef<AddFriendDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {},
    private socialService: SocialService,
    private dialog: MatDialog
  ) {}
  email: string;

  ngOnInit(): void {}
  onCancelClick(): void {
    this.dialogRef.close();
  }
  openDialog(title: string, desc: string) {
    this.dialog.open(DialogComponent, {
      data: {
        title: title,
        description: desc,
      },
    });
  }
  onAddClick(): void {
    this.socialService.addFriend(this.email).subscribe({
      next: (resp) => {
        this.openDialog('Congratulations', 'Friend request sent');
      },
      error: (e) => {
        if (e.status === 400) {
          this.openDialog(
            'An error occured',
            'Validating the email was not successful'
          );
        } else if (e.status === 404) {
          this.openDialog(
            'An error occured',
            'User not found with the given email'
          );
        } else if (e.status === 409) {
          this.openDialog(
            'An error occured',
            'Invite already sent or you are already friends'
          );
        } else {
          this.openDialog('An error occured', 'Unknown error');
        }
      },
      complete: () => {
        this.dialogRef.close();
      },
    });
  }
}
