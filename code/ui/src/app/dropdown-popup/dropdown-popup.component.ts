import { Component } from '@angular/core';
import {FormControl, FormGroup, ReactiveFormsModule} from '@angular/forms';
import {NgIf} from '@angular/common';
import {MatDialog} from '@angular/material/dialog';
import {GlobalConfigService} from '../../services/global-config/global-config.service';

@Component({
  selector: 'app-dropdown-popup',
  templateUrl: './dropdown-popup.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  styleUrls: ['./dropdown-popup.component.css']
})
export class DropdownPopupComponent {
  form = new FormGroup({
    choice: new FormControl('1')  // default to '1'
  });

  constructor(private globalConfig: GlobalConfigService,) { }

  isVisible = true;

  closePopup() {
    const selectedValue = this.form.get('choice')?.value;
    const selectedNumber = Number(selectedValue);
    console.log('Selected value:', selectedValue);
    this.globalConfig.READER_NUMBER = selectedNumber;
    this.isVisible = false;
  }
}
