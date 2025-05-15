import { Component } from '@angular/core';
import {FooterComponent} from "./footer/footer.component";
import {NgSwitchCase} from "@angular/common";
import {RouterOutlet} from "@angular/router";
import {HeaderComponent} from './header/header.component';
import {ImageBarComponent} from './main/image-bar/image-bar.component';
import {StationDisplayBarComponent} from './main/station-display-bar/station-display-bar.component';

@Component({
  selector: 'app-landing-page',
  imports: [
    FooterComponent,
    RouterOutlet,
    HeaderComponent,
    ImageBarComponent,
    StationDisplayBarComponent
  ],
  templateUrl: './landing-page.component.html',
  styleUrl: './landing-page.component.css'
})
export class LandingPageComponent {

}
