import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalConfigService} from '../../global-config/global-config.service';
import {
  DeleteStationResponseModel,
  StationListModel,
  StationModel,
  UpdateStationResponseModel
} from '../../../models/station-model';
import {Observable} from 'rxjs';
import {DeleteUserResponseModel} from '../../../models/user.model';

@Injectable({
  providedIn: 'root'
})
export class DeleteUserService {

  constructor(
    private httpClient: HttpClient,
    private globalConfig: GlobalConfigService,
  ) {}

  deleteUser(cardId: string): Observable<DeleteUserResponseModel> {
    console.log("deleting user with cardId :" + cardId);

    return this.httpClient.delete<DeleteUserResponseModel>(this.globalConfig.serverUrl + 'user' +'/' + cardId)
  }
}
