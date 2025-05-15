import { Injectable } from '@angular/core';
import {UserListModel, UserModel} from '../../models/user.model';
import {HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UpdateUserService {

  private apiUrl = 'http://localhost:8080/api/user'

  constructor(
    private httpClient: HttpClient,
  ) {}

  updateUser(user: UserModel) {

    // Zu passender Nachricht umwandeln
    const updateUserMessage = {
      cardId  : user.cardRfid,
      username: user.username,
    };

    this.httpClient.put(this.apiUrl, updateUserMessage).subscribe({
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
