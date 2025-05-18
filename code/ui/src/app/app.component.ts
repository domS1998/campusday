import {Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { GetUserDataService } from '../services/get-user-data/get-user-data.service';
import { FooterComponent } from './user-page/footer/footer.component';
import { UsbService } from '../services/usb-service/usb.service';
import { NgSwitch } from '@angular/common';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { UserPageComponent } from './user-page/user-page.component';
import { CommonModule } from '@angular/common';
import { GlobalConfigService } from '../services/global-config/global-config.service';

@Component({
  standalone: true,
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
  imports: [
    RouterOutlet,
    FooterComponent,
    CommonModule,
    NgSwitch,
    LandingPageComponent,
    UserPageComponent,
    // HeaderComponent,
  ],
  providers: [
    GetUserDataService]
})
export class AppComponent {

  protected readonly PageType = PageType;
  protected pageType: PageType = PageType.LANDING_PAGE;
  timerId: any;

  constructor(
    private userDataService: GetUserDataService,
    private usbService: UsbService,
    private globalConfig: GlobalConfigService,
  ) {}

  ngOnInit() {
    // Seite mit gegebener Kartennummer laden,
    //  wenn über USB eine Nachricht emfangen wird
    this.usbService.onMessage((msg) => {
      console.log("usb-sevice: load user \n" + msg + "");

      try {
        const message = JSON.parse(msg);
        this.globalConfig.CARD_NUMBER = message.cardNumber;
        this.globalConfig.READER_NUMBER = message.readerNumber;

        // Benutzer mit Kartennummer laden und zu globaler Config hinzufügen
        this.userDataService.loadUserdata(this.globalConfig.CARD_NUMBER).subscribe(
          userLoaded => {
            this.globalConfig.USER = userLoaded
          }
        )

        // Seite laden
        // this.load = true;
        this.pageType = PageType.USER_PAGE;

        // Nach 30s wieder zur Startseite
        this.startTimer(this.globalConfig.USERPAGE_TIMEOUT);
      }
      catch (err) {
        console.error('Invalid JSON:', err);
      }
    });
  }

  startTimer(seconds: number) {
    this.clearTimer(); // cancel existing timer
    this.timerId = setTimeout(() => {
      this.pageType = PageType.LANDING_PAGE;
    }, seconds * 1000);
  }

  clearTimer() {
    if (this.timerId) {
      clearTimeout(this.timerId);
      this.timerId = null;
      console.log('Timer reset');
    }
  }
}

export enum PageType {
  LANDING_PAGE,
  USER_PAGE
}

