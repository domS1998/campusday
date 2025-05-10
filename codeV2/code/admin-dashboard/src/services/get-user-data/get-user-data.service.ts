import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable, tap} from 'rxjs';
import {UserModel, UserWrapperModel} from '../../models/user.model';
import {UserStationModel} from '../../models/user-station-model';

@Injectable({
  providedIn: 'root'
})
export class GetUserDataService {
  // private apiUrl = 'http://localhost/services/user/0000-0001'
  private apiUrl = 'http://localhost:8080/api/user/0000-0001'
  // inject HttpClient instance
  constructor(
    private httpClient: HttpClient,
) {}

  // Nach Scannen der Karte Rfid auslesen aus Bluetooth
  //  bzw. ESP 32
  // z.B. ESP sendet Anfrage über localhost an Socket
  //  im UI Code im Browser, der dann den Server anfrägt
  public getData(): Observable<UserModel> {
    return this.httpClient.get<UserWrapperModel>(this.apiUrl).pipe(
      tap(response => console.log('Raw API response:\n', response)),  // Log the raw response
      map(response => {
        // Extract the 'user' field from the response and map it to UserModel
        const user = response?.user;
        // return model parsed from user as json
        return {
          username: user?.username ?? 'Unknown',
          cardRfid: user?.cardRfid ?? 'N/A',
          userStations: Array.isArray(user?.userStations)
            ? user.userStations.map((station: any) => this.mapToUserStationModel(station))
            : []  // Default to empty array if userStations is not an array
        };
      })
    );
  }

// Helper method to map a single station object to UserStationModel
  private mapToUserStationModel(station: any): UserStationModel {
    return {
      id: station.id,
      station: {
        name: station.station.name,
        number: station.station.number,
        location: {
          image: station.station.location.image,
          xcoordinate: station.station.location.xcoordinate,
          ycoordinate: station.station.location.ycoordinate
        }
      },
      completed: station.completed
    };
  }

}
