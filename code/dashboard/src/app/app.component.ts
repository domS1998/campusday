import {Component, inject} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {GetUserDataService} from '../services/user/get-user-data/get-user-data.service';
import {NavbarComponent} from './navbar/navbar.component';

@Component({
  standalone: true,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [
    RouterOutlet,
    NavbarComponent,
  ],
  providers: [
    GetUserDataService]
})
export class AppComponent {

  // private user: UserModel
  private title = 'Campus Day App'

  constructor(
  ) {}
}
