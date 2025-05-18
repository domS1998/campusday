import { Component } from '@angular/core';
import {GetUsersService} from '../../../services/user/get-users/get-users.service';
import {NgIf} from '@angular/common';
import {MatCardModule} from '@angular/material/card';
import {MatIcon} from '@angular/material/icon';
import {MatTableModule } from '@angular/material/table';
import {FormsModule } from '@angular/forms';
import {ViewEncapsulation } from '@angular/core';
import {MatInputModule} from '@angular/material/input';
import {AddStationFormComponent} from './add-station-form/add-station-form.component';
import {MatDialog} from '@angular/material/dialog';
import {Observable} from 'rxjs';
import {
  DeleteStationResponseModel,
  InsertStationResponseModel,
  StationListModel,
  StationModel,
  UpdateStationResponseModel
} from '../../../models/station-model';
import {GetStationsService} from '../../../services/station/get-stations/get-stations.service';
import {UpdateStationFormComponent} from './update-station-form/update-station-form.component';
import {UpdateUserService} from '../../../services/user/update-user/update-user.service';
import {UpdateStationService} from '../../../services/station/update-station/update-station.service';
import {InsertStationService} from '../../../services/station/insert-station/insert-station.service';
import {DeleteStationService} from '../../../services/station/delete-station/delete-station.service';


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
  stations!: StationModel[]; // Stationsliste

  // Flag für Ladestatus, damit div erst nach vollständigem
  //  Erhalt aller Benutzer geladen wird
  isLoaded = false;

  // Spaltennamen für mat-table
  displayedColumns: string[] = ['Name', 'Nummer', 'Stockwerk', 'Offset-X', 'Offset-Y', 'Aktionen'];

  constructor(
    private getUsersService: GetUsersService,
    private getStationsService: GetStationsService,
    private updateStationService: UpdateStationService,
    private insertStationService: InsertStationService,
    private deleteStationService: DeleteStationService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    this.getStationsService.getData().subscribe((response: StationListModel) => {
      this.stations = this.getStationsService.parseStationsFromJson(response.stations);
      this.isLoaded = true;
    });
  }

  // Benutzer löschen
  deleteStation(stationName: string): void {

    // Station aus Datenbank löschen
    const responseObs: Observable<DeleteStationResponseModel> = this.deleteStationService.deleteStation(stationName);

    responseObs.subscribe({

      // Bei Erfolg
      next: (response: DeleteStationResponseModel) => {
        console.log('response:\n', JSON.stringify(response, null, 5));

        // gelöschte Station aus Liste erfüllen
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

  // Form Popup öffnen für neue Station hinzuzufügen
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

        let responseObs: Observable<InsertStationResponseModel> = this.insertStationService.insertStation(stationReturned)

        responseObs.subscribe({
          next:  (response: InsertStationResponseModel) => {
            console.log('response:\n', JSON.stringify(response, null, 5));

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

        // Station updaten in der Datenbank
        let stationUpdatedObs: Observable<UpdateStationResponseModel> = this.updateStationService.updateStation(stationReturned)

        // Bei Antwort
        stationUpdatedObs.subscribe({
          // bei Erfolg
          next: (response: UpdateStationResponseModel) => {
            console.log('response:\n', JSON.stringify(response, null, 5));

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
