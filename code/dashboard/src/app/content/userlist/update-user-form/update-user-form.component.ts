import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogRef} from '@angular/material/dialog';
import {FloorPlanImage, StationModel} from '../../../../models/station-model';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatFormField} from '@angular/material/input';
import {UserModel} from '../../../../models/user.model';

@Component({
  selector: 'app-update-user-form',
  imports: [
    MatDialogActions,
    MatDialogContent,
    MatFormField,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './update-user-form.component.html',
  styleUrl: './update-user-form.component.css'
})
export class UpdateUserFormComponent {

  constructor(
    public dialogRef: MatDialogRef<UpdateUserFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data:{ userOld: UserModel},
  ) {}

  ngOnInit() {
    if (!this.data?.userOld) {
      console.warn('Dialog opened without userOld!');
      return;
    }
  }

  // Bei OK, Daten an dialogRef übergeben und Form schließen
  onOk(): void {
    this.dialogRef.close(this.data?.userOld);
  }

  // Bei Abbruch Form schließen
  onCancel(): void {
    this.dialogRef.close();
  }

  public setUsername(name: string): void {
    this.data.userOld.username = name;
  }
}
