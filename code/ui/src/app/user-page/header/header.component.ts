import {Component, Input} from '@angular/core';
import {StationDisplayBarComponent} from '../main/station-display-bar/station-display-bar.component';
import {UserModel} from '../../../models/user.model';
import {HttpClient} from '@angular/common/http';
import {GetUserDataService} from '../../../services/get-user-data/get-user-data.service';
import {GlobalConfigService} from '../../../services/global-config/global-config.service';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  protected readonly StationDisplayBarComponent = StationDisplayBarComponent;

  constructor(
    protected globalConfigService: GlobalConfigService,
  ) {
  }
}
