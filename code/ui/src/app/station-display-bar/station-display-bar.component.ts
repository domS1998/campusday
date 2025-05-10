import { Component } from '@angular/core';
import {CommonModule, NgForOf} from "@angular/common";
import {HttpClient} from '@angular/common/http';
import {GetUserDataService} from '../../services/get-user-data/get-user-data.service';
import {UserModel} from '../../models/user.model';
import {StationModel} from '../../models/station-model';
import {UserStationModel} from '../../models/user-station-model';
import {delay} from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import {FooterComponent} from '../footer/footer.component';

@Component({
  standalone: true,
  selector: 'app-station-display-bar',
  templateUrl: './station-display-bar.component.html',
  styleUrl: './station-display-bar.component.css',
  imports: [
    NgForOf,
    CommonModule,
  ]
})
export class StationDisplayBarComponent {

  public user!: UserModel

  // Liste der Stationen mit Statusinformation
  public userStations!: UserStationModel[];

  // Track loading state
  isLoaded = false;

  constructor( private userDataService: GetUserDataService,
               private httpClient: HttpClient
  ){}

  // Initialisierung der Komponente
  ngOnInit(): void {

    // Benutzerdaten laden
    this.userDataService.getData().subscribe(
      user => {
        console.log(user);

        // Falls Benutzer nicht existiert
        if (user.username == null) {
          // ...
        }
        this.user = user;
        this.userStations = this.user.userStations

        // console.log("userStations before sorting: \n")
        // this.userStations.forEach((userStation: UserStationModel) => {
          // console.log(userStation.station.number + "\n");
        // })

        // Stationen sortieren vor Anzeige
        this.sortStationsByNumber()

        // console.log("userStations after sorting: \n")
        // this.userStations.forEach((userStation: UserStationModel) => {
        //   console.log(userStation.station.number + "\n");
        // })

        // Observable als fertig geladen markieren
        this.isLoaded = true;
      }
    );
  }


  // UserStation Liste nach Stationsnummer sortiert mit Bubblesort
  sortStationsByNumber(): void {
    let n = this.userStations.length;
    let swapped: boolean;

    do {
      swapped = false;
      for (let i = 0; i < n - 1; i++) {

        let userStationCurrent: UserStationModel = this.userStations[i];
        let userStationNext   : UserStationModel = this.userStations[i+1];

        if (userStationCurrent.station.number > userStationNext.station.number) {
          // Swap elements
          [this.userStations[i], this.userStations[i+1]] = [this.userStations[i+1], this.userStations[i]];
          swapped = true;
        }
      }
      n--; // Each pass puts the biggest element at the end
    } while (swapped);
  }


}
