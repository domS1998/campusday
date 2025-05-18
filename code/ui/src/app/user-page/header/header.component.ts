import {Component, Input} from '@angular/core';
import {StationDisplayBarComponent} from '../main/station-display-bar/station-display-bar.component';
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
