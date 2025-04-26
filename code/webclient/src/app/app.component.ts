import {Component, inject} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {GetUserDataService} from '../services/get-user-data.service';
import {UserModel} from '../models/user.model';
import {StationDisplayBarComponent} from './station-display-bar/station-display-bar.component';
import {ImageBarComponent} from './image-bar/image-bar.component';
import {HttpClient} from '@angular/common/http';

@Component({
  standalone: true,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [
    RouterOutlet,
    StationDisplayBarComponent,
    ImageBarComponent,
  ],
  providers: [
    GetUserDataService]
})
export class AppComponent {

  // private user: UserModel
  private title = 'Campus Day App'

  constructor(
    // private userDataService: GetUserDataService,
    // private httpClient: HttpClient
  ) {
    // this.userDataService.getData().subscribe(user => {
    //   this.user = user;
    // });
    // console.log(this.user);
  }
}
