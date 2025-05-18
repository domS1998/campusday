import {Injectable} from '@angular/core';
import {UserModel} from '../../../models/user.model';
import {HttpClient} from '@angular/common/http';
import {GlobalConfigService} from '../../global-config/global-config.service';

@Injectable({
  providedIn: 'root'
})
export class UpdateUserService {

  // private apiUrl = 'http://localhost:8080/api/user'

  constructor(
    private httpClient: HttpClient,
    private globalConfig: GlobalConfigService,
  ) {}

  updateUser(user: UserModel) {

    // Zu passender Nachricht umwandeln
    const updateUserMessage = {
      cardId  : user.cardRfid,
      username: user.username,
    };

    this.httpClient.put(this.globalConfig.serverUrl + 'user', updateUserMessage).subscribe({
      next: (updateUserResponse) => {
        console.log('User updated successfully:', updateUserResponse);

      },
      error: (error) => {
        console.error('Error updating station:', error);
        // Show an error message to the user or handle retry logic
      },
      complete: () => {
        console.log('PUT request completed.');
      }
    });

  }
}
