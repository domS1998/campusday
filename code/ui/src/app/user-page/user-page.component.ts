import { Component } from '@angular/core';
import {FooterComponent} from './footer/footer.component';
import {HeaderComponent} from './header/header.component';
import {ImageBarComponent} from './main/image-bar/image-bar.component';
import {RouterOutlet} from '@angular/router';
import {StationDisplayBarComponent} from './main/station-display-bar/station-display-bar.component';

@Component({
  selector: 'app-user-page',
  imports: [
    FooterComponent,
    HeaderComponent,
    ImageBarComponent,
    RouterOutlet,
    StationDisplayBarComponent
  ],
  templateUrl: './user-page.component.html',
  styleUrl: './user-page.component.css'
})
export class UserPageComponent {

}
