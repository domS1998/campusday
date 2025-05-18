import {Component} from '@angular/core';
import {CommonModule, NgForOf} from '@angular/common';
import {UserStationModel} from '../../../../../models/user-station-model';
import {UserModel} from '../../../../../models/user.model';
import {FloorPlanImage, StationListModel, StationModel} from '../../../../../models/station-model';
import {GlobalConfigService} from '../../../../../services/global-config/global-config.service';
import {GetStationsService} from '../../../../../services/get-stations/get-stations.service';

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

  public constructor(
    private getStationsService: GetStationsService,
    protected globalConfig   : GlobalConfigService,
  ) {
    this.combinedList = this.floorLabels.map((label, index) => ({
      label,
      imageName: this.imageNames[index]
    }));
  }

  // Wenn Komponente initialisiert wird, Benuzterdaten von API laden
  ngOnInit(): void {
    this.getStationsService.getData().subscribe((response: StationListModel) => {
      this.globalConfig.STATIONS = this.getStationsService.parseStationsFromJson(response.stations);
      this.isLoaded = true;
    });
  }

  // Koordinaten berechnen
  calculateCoordinatesVh_Vw(station: StationModel): {xOffset: Number, yOffset: Number} {

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
      xOffset: station.location.xcoordinate,
      yOffset: station.location.ycoordinate
    }
  }

  protected readonly parseInt = parseInt;
}
