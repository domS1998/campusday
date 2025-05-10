import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogRef} from '@angular/material/dialog';
import {FloorPlanImage, StationModel} from '../../../../models/station-model';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MatFormField} from '@angular/material/input';

@Component({
  selector: 'app-update-station-form',
  imports: [
    MatDialogActions,
    MatDialogContent,
    MatFormField,
    ReactiveFormsModule,
    FormsModule
  ],
  templateUrl: './update-station-form.component.html',
  styleUrl: './update-station-form.component.css'
})
export class UpdateStationFormComponent {

  constructor(
    public dialogRef: MatDialogRef<UpdateStationFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data:{ stationOld: StationModel, stations: StationModel[] },
  ) {}

  // Bei OK, Daten an dialogRef übergeben und Form schließen
  onOk(): void {
    this.dialogRef.close(this.data.stationOld);
  }

  // Bei Abbruch Form schließen
  onCancel(): void {
    this.dialogRef.close();
  }

  public setStationName(name: string): void {

    // Bereits vorhandene Nummern abfangen
    this.data.stationOld.name = name;
  }

  public setStationNumber(numberStr: string): void {
    this.data.stationOld.number = Number(numberStr);
  }

  public setStationLocationImage(imageStr: string): void {
    // String zu enum casten
    this.data.stationOld.location.image = FloorPlanImage[imageStr as keyof typeof FloorPlanImage]
  }

  public setStationLocationXcoordinate(x: string): void {
    this.data.stationOld.location.xcoordinate = Number(x);
  }

  public setStationLocationYcoordinate(y: string): void {
    this.data.stationOld.location.ycoordinate = Number(y);
  }

}
