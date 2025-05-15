import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalConfig, Observable} from 'rxjs';
import {StationListModel, StationModel} from '../../models/station-model';
import {GlobalConfigService} from '../global-config/global-config.service';

@Injectable({
  providedIn: 'root'
})
export class GetStationsService {

  // inject HttpClient instance
  constructor(
    private httpClient: HttpClient,
    private globalConfig: GlobalConfigService,
  ) {}

  getData(): Observable<StationListModel> {
    const timestamp = new Date().getTime(); // Cachen von Get requests verhindern
    return this.httpClient.get<StationListModel>( this.globalConfig.serverUrl + 'station/all');
  }

  parseStationsFromJson(stations: StationModel[]): StationModel[] {
    return stations.map(station => ({
      name: station.name,
      number: station.number,
      location: {
        image: station.location.image,
        xcoordinate: station.location.xcoordinate,
        ycoordinate: station.location.ycoordinate
      }
    }));
  }
}
