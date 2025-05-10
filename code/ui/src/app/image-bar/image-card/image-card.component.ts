import {Component} from '@angular/core';
import {CommonModule, NgForOf} from '@angular/common';
import {GetUserDataService} from '../../../services/get-user-data/get-user-data.service';
import {UserStationModel} from '../../../models/user-station-model';
import {UserModel} from '../../../models/user.model';
import {FloorPlanImage} from '../../../models/station-model';

@Component({
  standalone: true,
  selector: 'app-image-card',
  templateUrl: './image-card.component.html',
  styleUrl: './image-card.component.css',
  imports: [
    NgForOf,
    CommonModule,
  ]
})
export class ImageCardComponent {

  public floorLabels: string[] = ['G2 - Stockwerk 2', 'G2 - Stockwerk 1', 'G2 - Erdgeschoss'];
  public imageNames : string[] = ['svg/stock2.svg', 'svg/stock1.svg', 'svg/stock0.svg'];
  public combinedList: { label: string; imageName: string }[] = [];
  public user!: UserModel
  public userStations!: UserStationModel[];
  public isLoaded = false;
  pinOffsets = [{x: 30.5, y: 19}, {x: 30.5, y: 44}, {x: 30.5, y: 69}]; // list to trigger *ngFor 3 times
  // pinOffsets = [{x: 0, y: 0}, {x: 40, y: 55}, {x: 40, y: 65}]; // list to trigger *ngFor 3 times


  public constructor(
    private userDataService: GetUserDataService,
  ) {
    this.combinedList = this.floorLabels.map((label, index) => ({
      label,
      imageName: this.imageNames[index]
    }));
  }

  // Wenn Komponente initialisiert wird, Benuzterdaten von API laden
  ngOnInit(): void {
    // Benutzerdaten laden und zuweisen
    this.userDataService.getData().subscribe(
      user => {
        this.user = user;
        this.userStations = this.user.userStations
        this.isLoaded = true;

        console.log(user);
        this.userStations.forEach(userStation => {
          console.log("----> " + userStation.station.location.image);
        })

      }
    );
  }

  // Koordinaten berechnen
  calculateCoordinates(userStation: UserStationModel): {xOffset: Number, yOffset: Number} {

    //...

    // Offsets, für Stationsmarkierung relativ zu einem Bild zu plazieren
    let OFFSET_FLOOR_X = 400 + userStation.station.location.xcoordinate
    let OFFSET_FLOOR_Y = 170 + userStation.station.location.ycoordinate

    console.log(userStation.station.location.image);
    console.log(FloorPlanImage.G20);
    console.log(FloorPlanImage.G21);
    console.log(FloorPlanImage.G22);
    console.log(userStation.station.location.image == FloorPlanImage.G20);
    console.log(userStation.station.location.image == FloorPlanImage.G21);
    console.log(userStation.station.location.image == FloorPlanImage.G22);

    switch (userStation.station.location.image) {
      case FloorPlanImage.G20:
        console.log("G20");
        break;
      case FloorPlanImage.G21:
        console.log("G21");
        OFFSET_FLOOR_Y = OFFSET_FLOOR_Y +  290;
        break;
      case FloorPlanImage.G22:
        console.log("G22");
        OFFSET_FLOOR_Y = OFFSET_FLOOR_Y + 600;
        break;
    }


    console.log("Station " + userStation.station.number + ": " + OFFSET_FLOOR_X, OFFSET_FLOOR_Y);

    return {
      xOffset: OFFSET_FLOOR_X,
      yOffset: OFFSET_FLOOR_Y
    }
  }

  // Koordinaten berechnen
  calculateCoordinatesVh_Vw(userStation: UserStationModel): {xOffset: Number, yOffset: Number} {

    //...

    // // Offsets, für Stationsmarkierung relativ zu einem Bild zu plazieren
    // let OFFSET_FLOOR_X = 400 + userStation.station.location.xcoordinate
    // let OFFSET_FLOOR_Y = 170 + userStation.station.location.ycoordinate
    //
    // console.log(userStation.station.location.image);
    // console.log(FloorPlanImage.G20);
    // console.log(FloorPlanImage.G21);
    // console.log(FloorPlanImage.G22);
    // console.log(userStation.station.location.image == FloorPlanImage.G20);
    // console.log(userStation.station.location.image == FloorPlanImage.G21);
    // console.log(userStation.station.location.image == FloorPlanImage.G22);
    //
    // switch (userStation.station.location.image) {
    //   case FloorPlanImage.G20:
    //     console.log("G20");
    //     break;
    //   case FloorPlanImage.G21:
    //     console.log("G21");
    //     OFFSET_FLOOR_Y = OFFSET_FLOOR_Y +  290;
    //     break;
    //   case FloorPlanImage.G22:
    //     console.log("G22");
    //     OFFSET_FLOOR_Y = OFFSET_FLOOR_Y + 600;
    //     break;
    // }
    //
    //
    // console.log("Station " + userStation.station.number + ": " + OFFSET_FLOOR_X, OFFSET_FLOOR_Y);
    //
    // return {
    //   xOffset: OFFSET_FLOOR_X,
    //   yOffset: OFFSET_FLOOR_Y
    // }

    return {
      xOffset: userStation.station.location.xcoordinate,
      yOffset: userStation.station.location.ycoordinate
    }
  }

}
