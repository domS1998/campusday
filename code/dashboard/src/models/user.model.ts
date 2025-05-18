import {UserStationModel} from './user-station-model';
import {Inject} from '@angular/core';

export interface UserModel {
  username: string;
  cardRfid: string;
  userStations: UserStationModel[];
}

export class UserModel {
  username: string = "";
  cardRfid: string = "";
  userStations: UserStationModel[] = [];
}

export class UserWrapperModel {
  user!: UserModel;
}

export class UserListModel {
  users!: UserModel[];
}

export interface InsertUserMessageModel {
  username: string;
  cardId: string;
}

export interface InsertUserMessageResponseModel {
  usernameExits: boolean
  cardIdExits: boolean
  success: boolean
}

export interface DeleteUserResponseModel {
  deleted: boolean
}
