import { Component } from '@angular/core';
import {MatSidenav, MatSidenavContainer, MatSidenavContent, MatSidenavModule} from '@angular/material/sidenav';
import {MatDivider, MatListItem, MatNavList} from '@angular/material/list';
import {MatIcon} from '@angular/material/icon';
import {RouterOutlet} from '@angular/router';
import {MatToolbar} from '@angular/material/toolbar';
import {MatButton, MatIconButton} from '@angular/material/button';
import {MatExpansionPanel, MatExpansionPanelHeader} from '@angular/material/expansion';
import {MatMenu, MatMenuTrigger} from '@angular/material/menu';
import {UserlistComponent} from '../content/userlist/userlist.component';
import {StationlistComponent} from '../content/stationlist/stationlist.component';
import {MatDialog} from '@angular/material/dialog';
import {NgIf} from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [
    MatToolbar,
    MatIcon,
    MatNavList,
    MatExpansionPanelHeader,
    MatListItem,
    MatExpansionPanel,
    MatSidenav,
    MatSidenavContainer,
    RouterOutlet,
    MatSidenavModule,
    UserlistComponent,
    StationlistComponent,
    NgIf,
  ],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent {
  public contentComponent: ContentComponent = ContentComponent.DEFAULT;
  protected readonly ContentComponent = ContentComponent;

  setUserList(): void {
    if (this.contentComponent != ContentComponent.USERLIST) {
      this.contentComponent = ContentComponent.USERLIST;
      console.log(this.contentComponent);
    }
  }

  setStationList(): void {
    if (this.contentComponent != ContentComponent.STATIONLIST) {
      this.contentComponent = ContentComponent.STATIONLIST;
      console.log(this.contentComponent);
    }
  }

}

export enum ContentComponent {
  DEFAULT     ,
  USERLIST    ,
  STATIONLIST ,
}
