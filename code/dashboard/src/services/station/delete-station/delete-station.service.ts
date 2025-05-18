import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalConfigService} from '../../global-config/global-config.service';
import {
  DeleteStationResponseModel,
  StationListModel,
  StationModel,
  UpdateStationResponseModel
} from '../../../models/station-model';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DeleteStationService {

  // private apiUrl = 'http://localhost:8080/api/user'

  constructor(
    private httpClient: HttpClient,
    private globalConfig: GlobalConfigService,
  ) {}

  deleteStation(stationName: string): Observable<DeleteStationResponseModel> {
    console.log("deleting station :" + stationName);

    return this.httpClient.delete<DeleteStationResponseModel>(this.globalConfig.serverUrl + 'station' +'/' + stationName)
  }
}
