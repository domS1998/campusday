import {Component, inject} from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {GetUserDataService} from '../services/get-user-data/get-user-data.service';
import {UserModel} from '../models/user.model';
import {StationDisplayBarComponent} from './station-display-bar/station-display-bar.component';
import {ImageBarComponent} from './image-bar/image-bar.component';
import {HttpClient} from '@angular/common/http';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {UsbService} from '../services/usb-service/usb.service';
import {NgIf} from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [
    RouterOutlet,
    StationDisplayBarComponent,
    ImageBarComponent,
    HeaderComponent,
    FooterComponent,
    NgIf,
    // HeaderComponent,
  ],
  providers: [
    GetUserDataService]
})
export class AppComponent {

  // Kartennummer, um Benutzer zu laden
  private cardNumber = ""

  // Kartenlesegerätsnummer, um im UI den verwendeten Kartenleser zu markieren
  private readerNumber = ""

  // Schalter zu laden,
  //  Seite erst laden, wenn Signal von Karte über USB eingeht
  protected load: boolean = false;

  constructor(
    // private userDataService: GetUserDataService,
    // private httpClient: HttpClient
    private usbService: UsbService,
  ) {

    // this.userDataService.getData().subscribe(user => {
    //   this.user = user;
    // });
    // console.log(this.user);
  }

  ngOnInit() {

    // Seite mit gegebener Kartennummer laden,
    //  wenn über USB eine Nachricht emfangen wird
    this.usbService.onMessage((msg) => {
      console.log("usb-sevice: load user \n" + msg + "");

      try {
        const obj = JSON.parse(msg);
        this.readerNumber = obj.readerNumber;
        this.cardNumber = obj.cardNumber;
        console.log(this.readerNumber); // "1"
        console.log(this.cardNumber);   // "0000-0001"

        // Seite laden
        this.load = true;
      }
      catch (err) {
        console.error('Invalid JSON:', err);
      }
    });
  }
}
