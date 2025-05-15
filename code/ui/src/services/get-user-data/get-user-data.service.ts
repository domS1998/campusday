import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map, Observable, tap} from 'rxjs';
import {UserModel, UserWrapperModel} from '../../models/user.model';
import {UserStationModel} from '../../models/user-station-model';
import {NoCacheInterceptor} from '../no-cache-interceptor/no-cache-interceptor.service';

@Injectable({
  providedIn: 'root'
})
export class GetUserDataService {

  private apiUrl = 'http://localhost:8080/api/user'
  public user: UserModel | null = null;

  constructor(
    private httpClient: HttpClient,
  ) {}

  public loadUserdata(cardRfId: string): Observable<UserModel> {

    console.log('Loading user with card \'' + cardRfId +'\'' );

    return this.httpClient.get<UserWrapperModel>(this.apiUrl+'/'+cardRfId).pipe(
      tap(response => console.log('Raw API response:\n', response)),  // Log the raw response
      map(response => {
        // Extract the 'user' field from the response
        const user = response?.user;

        // Store the mapped result before returning
        const result: UserModel = {
          username: user?.username ?? 'Unknown',
          cardRfid: user?.cardRfid ?? 'N/A',
          userStations: Array.isArray(user?.userStations)
            ? user.userStations.map((station: any) => this.mapToUserStationModel(station))
            : []  // Default to empty array if userStations is not an array
        };
        this.user = result;
        return result;
      })
    );
  }

// UserStation JSON zu UserStationModel
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
