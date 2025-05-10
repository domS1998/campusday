import { Component } from '@angular/core';
import {UserListModel, UserModel} from '../../../models/user.model';
import {Router} from 'express';
import {GetUsersService} from '../../../services/get-users/get-users.service';
import {HttpClient} from '@angular/common/http';
import {NgForOf, NgIf} from '@angular/common';
import {MatCard, MatCardHeader, MatCardModule, MatCardSubtitle} from '@angular/material/card';
import {MatList, MatListItem} from '@angular/material/list';
import {MatIcon} from '@angular/material/icon';
import {MatTable} from '@angular/material/table';
import { MatTableModule } from '@angular/material/table';
import { FormsModule } from '@angular/forms';
import { ViewEncapsulation } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatFormField, MatInputModule} from '@angular/material/input';
import {AddStationFormComponent} from './add-station-form/add-station-form.component';
import {MatDialog} from '@angular/material/dialog';
import {Observable} from 'rxjs';
import {StationListModel, StationModel} from '../../../models/station-model';
import {GetStationsService} from '../../../services/get-stations/get-stations.service';
import {UpdateStationFormComponent} from './update-station-form/update-station-form.component';


@Component({
  standalone: true,
  selector: 'app-stationlist',
  imports: [
    NgIf,
    MatIcon,
    MatCardModule,
    FormsModule,
    MatTableModule,
    MatInputModule,
    FormsModule,
  ],
  templateUrl: './stationlist.component.html',
  styleUrl: './stationlist.component.css',
  encapsulation: ViewEncapsulation.None  // Disable view encapsulation
})
export class StationlistComponent {

  // users: UserModel[]; // Benutzerliste
  stations: StationModel[]; // Stationsliste

  // Flag für Ladestatus, damit div erst nach vollständigem
  //  Erhalt aller Benutzer geladen wird
  isLoaded = false;

  // Spaltennamen für mat-table
  displayedColumns: string[] = ['Name', 'Nummer', 'Stockwerk', 'Offset-X', 'Offset-Y', 'Aktionen'];

  constructor(
    private getUsersService: GetUsersService,
    private getStationsService: GetStationsService,
    private httpClient: HttpClient,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getStationsService.getData().subscribe((response: StationListModel) => {
      this.stations = this.getStationsService.parseStationsFromJson(response.stations);
      // console.log(response);
      // console.log(this.users); // Now you have the real UserModel[]
      // console.log(JSON.stringify(this.users, null, 4)); // Pretty-printed output
      // this.fillDataSource()
      // console.log(JSON.stringify(this.dataSource, null, 5)); // Pretty-printed output
      this.isLoaded = true;
    });
  }

  // Benutzer löschen
  deleteUser(stationName: string): void {

    // 1.) DELETE user/...

    // 2.) Benutzer aus Liste entfernen, falls erfolgreich

    this.httpClient.delete('http://localhost:8080/api/station/' + stationName, ).subscribe({

      // Bei Erfolg
      next: (updatedUser) => {
        console.log('Station deleted successfully:', updatedUser);

        // gelöschten Benutzer aus Liste erfüllen
        this.stations = this.stations.filter(item => item.name !== stationName);

      },
      error: (error) => {
        console.error('Error deleting station:', error);
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
  openAddStationDialog(): void {

    let stationNew: StationModel = new StationModel();

    console.log("new station before input: \n" + JSON.stringify(stationNew, null, 5)); // Pretty-printed output

    const dialogRef = this.dialog.open(AddStationFormComponent, {
      width: '20vw',
      data: { stationNew: stationNew, stations: this.stations }
    });

    dialogRef.afterClosed().subscribe(stationReturned => {
      if (stationReturned) {
        console.log("new station received from form dialog: \n" + JSON.stringify(stationReturned, null, 5)); // Pretty-printed output

        // Zu passender Nachricht umwandeln
        const addStationMessage = {
          name: stationReturned.name,
          number: stationReturned.number,
          imageLabel: stationReturned.location.image,
          xcoordinate: stationReturned.location.xcoordinate,
          ycoordinate: stationReturned.location.ycoordinate,
        };

        console.log("new station in AddStationMessage: \n" + JSON.stringify(addStationMessage, null, 5)); // Pretty-printed output

        this.httpClient.post('http://localhost:8080/api/station', addStationMessage).subscribe({
          next: (postedStation) => {
            console.log('Station added successfully:', postedStation);

            // Station laden und in Liste einfügen
            this.isLoaded = false;
            this.getStationsService.getData().subscribe((response: StationListModel) => {
              console.log(response);
              this.stations = this.getStationsService.parseStationsFromJson(response.stations);
              this.isLoaded = true;
            });
          },
          error: (error) => {
            console.error('Error storing station:', error);
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
  openUpdateStationDialog(stationOld: StationModel): void {

    const dialogRef = this.dialog.open(UpdateStationFormComponent, {
      width: '20vw',
      data: { stationOld: stationOld, stations: this.stations }
    });
    dialogRef.afterClosed().subscribe(stationReturned => {
      if (stationReturned) {
        console.log("updated station received from form dialog: \n" + JSON.stringify(stationReturned, null, 5)); // Pretty-printed output

        // Zu passender Nachricht umwandeln
        const updateStationMessage = {
          name: stationReturned.name,
          number: stationReturned.number,
          imageLabel: stationReturned.location.image,
          xcoordinate: stationReturned.location.xcoordinate,
          ycoordinate: stationReturned.location.ycoordinate,
        };

        console.log("updated station in AddStationMessage: \n" + JSON.stringify(updateStationMessage, null, 5)); // Pretty-printed output

        this.httpClient.put('http://localhost:8080/api/station', updateStationMessage).subscribe({
          next: (postedStation) => {
            console.log('Station updated successfully:', postedStation);

            // Station laden und in Liste einfügen
            this.isLoaded = false;
            this.getStationsService.getData().subscribe((response: StationListModel) => {
              this.stations = this.getStationsService.parseStationsFromJson(response.stations);
              this.isLoaded = true;
            });
          },
          error: (error) => {
            console.error('Error updating station:', error);
            // Show an error message to the user or handle retry logic
          },
          complete: () => {
            console.log('PUT request completed.');
          }
        });
      }
    });
  }


}
