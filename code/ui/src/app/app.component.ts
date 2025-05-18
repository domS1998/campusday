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
import {DropdownPopupComponent} from './dropdown-popup/dropdown-popup.component';
import {MatDialog} from '@angular/material/dialog';

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
    DropdownPopupComponent,
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
    public dialog: MatDialog
  ) {}

  ngOnInit() {

    // Readernummer festlegen
    this.openAddStationDialog()

    // Seite mit gegebener Kartennummer laden,
    //  wenn über USB eine Nachricht emfangen wird
    this.usbService.onMessage((msg) => {
      console.log("usb-sevice: load user \n" + msg + "");

      try {
        const message = JSON.parse(msg);
        this.globalConfig.CARD_NUMBER = message.cardNumber;

        if (this.globalConfig.READER_NUMBER == message.readerNumber;) {
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


  openAddStationDialog(): void {

    // let readerNumber = -1

    const dialogRef = this.dialog.open(DropdownPopupComponent, {
      width: '20vw',
      // data: { readerNumber }
    });

    dialogRef.afterClosed().subscribe(readerNumberReturned => {
      this.globalConfig.CARD_NUMBER = readerNumberReturned;
    });
  }


}

export enum PageType {
  LANDING_PAGE,
  USER_PAGE
}

