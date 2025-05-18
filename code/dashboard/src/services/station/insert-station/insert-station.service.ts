import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalConfigService} from '../../global-config/global-config.service';
import {InsertStationResponseModel, StationModel, UpdateStationResponseModel} from '../../../models/station-model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InsertStationService {

  // private apiUrl = 'http://localhost:8080/api/user'

  constructor(
    private httpClient: HttpClient,
    private globalConfig: GlobalConfigService,
  ) {}

  insertStation(station: StationModel): Observable<InsertStationResponseModel> {

    // Zu passender Nachricht umwandeln
    const insertStationMessage = {
      name: station.name,
      number: station.number,
      imageLabel: station.location.image,
      xcoordinate: station.location.xcoordinate,
      ycoordinate: station.location.ycoordinate,
    };

    console.log("Inserting new station: \n" + JSON.stringify(insertStationMessage, null, 5)); // Pretty-printed output

    return this.httpClient.post<InsertStationResponseModel>(this.globalConfig.serverUrl + 'station', insertStationMessage)

  }
}
