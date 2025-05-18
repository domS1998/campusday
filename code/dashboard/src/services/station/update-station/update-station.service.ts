import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalConfigService} from '../../global-config/global-config.service';
import {StationListModel, StationModel, UpdateStationResponseModel} from '../../../models/station-model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UpdateStationService {

  // private apiUrl = 'http://localhost:8080/api/user'

  constructor(
    private httpClient: HttpClient,
    private globalConfig: GlobalConfigService,
  ) {}

  updateStation(station: StationModel): Observable<UpdateStationResponseModel> {

    // Zu passender Nachricht umwandeln
    const updateStationMessage = {
      name: station.name,
      number: station.number,
      imageLabel: station.location.image,
      xcoordinate: station.location.xcoordinate,
      ycoordinate: station.location.ycoordinate,
    };

    console.log("updated station in AddStationMessage: \n" + JSON.stringify(updateStationMessage, null, 5)); // Pretty-printed output

    return this.httpClient.put<UpdateStationResponseModel>(this.globalConfig.serverUrl + 'station', updateStationMessage)

  }
}
