import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserListModel, UserModel} from '../../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class GetUsersService {
  // private apiUrl = 'http://localhost/services/user/0000-0001'
  private apiUrl = 'http://localhost:8080/api/user/0000-0001'

  // inject HttpClient instance
  constructor(
    private httpClient: HttpClient,
  ) {}

  getData(): Observable<UserListModel> {
    return this.httpClient.get<UserListModel>('http://localhost:8080/api/user/all');
  }

  parseUsersFromJson(users: UserModel[]): UserModel[] {
    return users.map(u => ({
      username: u.username,
      cardRfid: u.cardRfid,
      userStations: u.userStations.map(us => ({
        id: us.id,
        completed: us.completed,
        station: {
          name: us.station.name,
          number: us.station.number,
          location: {
            image: us.station.location.image,
            xcoordinate: us.station.location.xcoordinate,
            ycoordinate: us.station.location.ycoordinate
          }
        }
      }))
    }));
  }

}
