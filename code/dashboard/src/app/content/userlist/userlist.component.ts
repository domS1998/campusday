import { Component } from '@angular/core';
import {
  DeleteUserResponseModel,
  InsertUserMessageResponseModel,
  UserListModel,
  UserModel
} from '../../../models/user.model';
import {GetUsersService} from '../../../services/user/get-users/get-users.service';
import {NgIf} from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import {MatIcon} from '@angular/material/icon';
import {MatTableModule } from '@angular/material/table';
import {FormsModule } from '@angular/forms';
import {ViewEncapsulation } from '@angular/core';
import {MatInputModule} from '@angular/material/input';
import {InputDialogComponent} from './add-user-form/input-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {UpdateUserFormComponent} from './update-user-form/update-user-form.component';
import {UpdateUserService} from '../../../services/user/update-user/update-user.service';
import {InsertUserService} from '../../../services/user/insert-user/insert-user.service';
import {Observable} from 'rxjs';
import {DeleteUserService} from '../../../services/user/delete-user/delete-user.service';


@Component({
  selector: 'app-userlist',
  imports: [
    NgIf,
    MatIcon,
    MatCardModule,
    FormsModule,
    MatTableModule,
    MatInputModule,
    FormsModule,
  ],
  templateUrl: './userlist.component.html',
  styleUrl: './userlist.component.css',
  encapsulation: ViewEncapsulation.None  // Disable view encapsulation
})
export class UserlistComponent {

  users!: UserModel[]; // Benutzerliste

  // Flag für Ladestatus, damit div erst nach vollständigem
  //  Erhalt aller Benutzer geladen wird
  isLoaded = false;

  // Spaltennamen für mat-table
  displayedColumns: string[] = ['Benutzername', 'Kartennummer', 'Aktionen'];

  constructor(
    private getUsersService: GetUsersService,
    private updateUserService: UpdateUserService,
    private insertUserService: InsertUserService,
    private deleteUserService: DeleteUserService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getUsersService.getData().subscribe((response: UserListModel) => {
      this.users = this.getUsersService.parseUsersFromJson(response.users);
      console.log(JSON.stringify(this.users, null, 4)); // Pretty-printed output
      this.isLoaded = true;
    });
  }

  // Benutzer löschen
  deleteUser(cardId: string): void {

    const responseObs: Observable<DeleteUserResponseModel> = this.deleteUserService.deleteUser(cardId);

    responseObs.subscribe({

      // Bei Erfolg
      next: (response: DeleteUserResponseModel) => {
        console.log('response:\n', JSON.stringify(response, null, 5));

        // gelöschten Benutzer aus Liste erfüllen
        this.users = this.users.filter(item => item.cardRfid !== cardId);

      },
      error: (error) => {
        console.error('Error deleting user:', error);
        // Show an error message to the user or handle retry logic
      },
      complete: () => {
        console.log('DELETE request completed.');
      }
    });

  }

  // Stationsliste für Benutzer anzeigen
  showDetails(username: string): void {

  }

  // für Form Popup
  openDialog(): void {
    const dialogRef = this.dialog.open(InputDialogComponent, {
      width: '20vw',
      data: {username: '', cardRfid: ''},
    });

    dialogRef.afterClosed().subscribe(userReturned => {
      if (userReturned) {
        console.log('Received from dialog:', userReturned);

        const responseObs: Observable<InsertUserMessageResponseModel> = this.insertUserService.insertUser(userReturned);

        responseObs.subscribe({
          next: (response: InsertUserMessageResponseModel) => {
            console.log('response:\n', JSON.stringify(response, null, 5));

            // Benutzer laden und in Liste einfügen
            this.isLoaded = false;
            this.getUsersService.getData().subscribe((response: UserListModel) => {
              this.users = this.getUsersService.parseUsersFromJson(response.users);
              this.isLoaded = true;
            });
          },
          error: (error) => {
            console.error('Error storing user:', error);
            // Show an error message to the user or handle retry logic
          },
          complete: () => {
            console.log('POST request completed.');
          }
        });
      }
    });
  }

  // für Form Popup
  openUpdateUserDialog(userOld: UserModel): void {

    console.log("user received through edit button: \n" + userOld);

    const dialogRef = this.dialog.open(UpdateUserFormComponent, {
      width: '20vw',
      data: { userOld: userOld}
    });
    dialogRef.afterClosed().subscribe(userReturned => {
      if (userReturned) {
        // Pretty-print Json
        console.log("updated user received from form dialog: \n" + JSON.stringify(userReturned, null, 5));
        this.updateUserService.updateUser(userReturned)
      }
    });
  }

}
