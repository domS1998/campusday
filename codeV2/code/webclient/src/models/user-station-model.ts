import {UserModel} from './user.model';
import {StationModel} from './station-model';

export interface UserStationModel {
  id: String;
  station: StationModel;
  completed: boolean;
}

