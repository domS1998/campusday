import {UserStationModel} from './user-station-model';
import {Inject} from '@angular/core';


export interface UserModel {
  username: string;
  cardRfid: string;
  userStations: UserStationModel[];
}

export class UserWrapperModel {
  user!: UserModel;
}

export class UserListModel {
  users!: UserModel[];
}

