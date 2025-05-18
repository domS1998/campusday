import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalConfigService} from '../../global-config/global-config.service';
import {InsertStationResponseModel, StationModel} from '../../../models/station-model';
import {Observable} from 'rxjs';
import {InsertUserMessageResponseModel, UserModel} from '../../../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class InsertUserService {

  constructor(
    private httpClient: HttpClient,
    private globalConfig: GlobalConfigService,
  ) {}

  insertUser(user: any): Observable<InsertUserMessageResponseModel> {

    // Zu passender Nachricht umwandeln
    const insertUserMessage = {
      username: user.username,
      cardId: user.cardId,
    };

    console.log("Inserting new user: \n" + JSON.stringify(insertUserMessage, null, 5)); // Pretty-printed output
    return this.httpClient.post<InsertUserMessageResponseModel>(this.globalConfig.serverUrl + 'user', insertUserMessage)
  }
}
