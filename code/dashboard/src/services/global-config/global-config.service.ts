import { Injectable } from '@angular/core';
import {UserModel} from '../../models/user.model';
import {StationModel} from '../../models/station-model';

@Injectable({ providedIn: 'root' })
export class GlobalConfigService {
  appVersion = '1.0.0';
  protocol   = 'http'
  // host       = 'localhost';
  host       = 'campusdayapp.germanywestcentral.cloudapp.azure.com';
  port       = '8080';
  apiPrefix  = 'api';
  serverUrl  = this.protocol+'://'+this.host+':'+this.port+'/'+this.apiPrefix+'/'
  user!: UserModel;
  stations!: StationModel [];
  cardNumber   = ''
  readerNumber = 0
  userpageTimeout = 5
}

