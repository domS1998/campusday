import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserListModel} from '../../models/user.model';
import {StationListModel, StationModel} from '../../models/station-model';

@Injectable({
  providedIn: 'root'
})
export class GetStationsService {

  // private apiUrl = 'http://localhost/services/user/0000-0001'
  private apiUrl = 'http://localhost:8080/api/user/0000-0001'

  // inject HttpClient instance
  constructor(
    private httpClient: HttpClient,
  ) {}

  getData(): Observable<StationListModel> {
    const timestamp = new Date().getTime(); // Cachen von Get requests verhindern
    return this.httpClient.get<StationListModel>('http://localhost:8080/api/station/all'+`?t=${timestamp}`);
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
