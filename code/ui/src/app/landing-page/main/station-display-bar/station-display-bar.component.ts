import { Component } from '@angular/core';
import {CommonModule, NgForOf} from "@angular/common";
import {GetUserDataService} from '../../../../services/get-user-data/get-user-data.service';
import {UserModel} from '../../../../models/user.model';
import {UserStationModel} from '../../../../models/user-station-model';
import {GlobalConfigService} from '../../../../services/global-config/global-config.service';

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

  constructor(private userDataService: GetUserDataService,
              protected globalConfig: GlobalConfigService,
  ){}

  // Initialisierung der Komponente
  ngOnInit(): void {

    // Benutzerdaten laden
    // this.userDataService.loadUserdata(this.globalConfig.cardNumber).subscribe(
    //   user => {
    //     console.log(user);
    //
    //     // Falls Benutzer nicht existiert
    //     if (user.username == null) {
    //       // ...
    //     }
    //     this.user = user;
    //     this.userStations = this.user.userStations
    //
    //     // Stationen sortieren vor Anzeige
    //     this.sortStationsByNumber()
    //
    //     // Observable als fertig geladen markieren
        this.isLoaded = true;
    //   }
    // );
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
