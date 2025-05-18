import { Injectable } from '@angular/core';
import {UserModel} from '../../models/user.model';
import {StationModel} from '../../models/station-model';

@Injectable({ providedIn: 'root' })
export class GlobalConfigService {
  APP_VERSION = '1.0.0';
  PROTOCOL    = 'http'
  SERVER_HOST = 'localhost';
  SERVER_PORT = '8080';
  API_PREFIX  = 'api';
  SERVER_URL  = this.PROTOCOL+'://'+this.SERVER_HOST+':'+this.SERVER_PORT+'/'+this.API_PREFIX+'/'
  USER!: UserModel;
  STATIONS!: StationModel [];
  CARD_NUMBER      = ''
  READER_NUMBER    = 0
  USERPAGE_TIMEOUT = 5
  // WEBSOCKET_HOST = 'localhost'
  WEBSOCKET_HOST = 'campusdayapp.germanywestcentral.cloudapp.azure.com'
  // WEBSOCKET_PORT = '8000'
  WEBSOCKET_PORT = '8080/ws'
}
