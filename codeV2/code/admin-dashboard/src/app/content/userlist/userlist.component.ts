import { Component } from '@angular/core';
import {UserListModel, UserModel} from '../../../models/user.model';
import {GetUsersService} from '../../../services/get-users/get-users.service';
import {HttpClient} from '@angular/common/http';
import {NgIf} from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import {MatIcon} from '@angular/material/icon';
import {MatTableModule } from '@angular/material/table';
import {FormsModule } from '@angular/forms';
import {ViewEncapsulation } from '@angular/core';
import {MatInputModule} from '@angular/material/input';
import {InputDialogComponent} from './add-user-form/input-dialog.component';
import {MatDialog} from '@angular/material/dialog';


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

  users: UserModel[]; // Benutzerliste

  // Flag für Ladestatus, damit div erst nach vollständigem
  //  Erhalt aller Benutzer geladen wird
  isLoaded = false;

  // Spaltennamen für mat-table
  displayedColumns: string[] = ['Benutzername', 'Kartennummer', 'Aktionen'];

  constructor(
    private getUsersService: GetUsersService,
    private httpClient: HttpClient,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getUsersService.getData().subscribe((response: UserListModel) => {
      this.users = this.getUsersService.parseUsersFromJson(response.users);
      // console.log(response);
      // console.log(this.users); // Now you have the real UserModel[]
      console.log(JSON.stringify(this.users, null, 4)); // Pretty-printed output
      // this.fillDataSource()
      // console.log(JSON.stringify(this.dataSource, null, 5)); // Pretty-printed output
      this.isLoaded = true;
    });
  }

  // Benutzer löschen
  deleteUser(cardId: string): void {

    // 1.) DELETE user/...

    // 2.) Benutzer aus Liste entfernen, falls erfolgreich

    this.httpClient.delete('http://localhost:8080/api/user/'+cardId, ).subscribe({

      // Bei Erfolg
      next: (updatedUser) => {
        console.log('User successfully deleted:\n', updatedUser);

        // gelöschten Benutzer aus Liste erfüllen
        this.users = this.users.filter(item => item.cardRfid !== cardId);

      },
      error: (error) => {
        console.error('Error deleting user:', error);
        // Show an error message to the user or handle retry logic
      },
      complete: () => {
        console.log('POST request completed.');
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

        // this.httpClient.delete(`users/${result.user_id}`).subscribe((response) => {})

        console.log(" + " + userReturned);

        this.httpClient.post('http://localhost:8080/api/user', userReturned).subscribe({
          next: (updatedUser) => {
            console.log('User updated successfully:', updatedUser);
            // You can update local state here or show a success message

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


}
