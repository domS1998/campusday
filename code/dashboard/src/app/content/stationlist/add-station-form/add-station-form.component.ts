import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogRef} from '@angular/material/dialog';
import {FormsModule} from '@angular/forms';
import {MatFormField} from '@angular/material/input';
import {FloorPlanImage, StationModel} from '../../../../models/station-model';

@Component({
  selector: 'app-add-user-form',
  imports: [
    FormsModule,
    MatDialogActions,
    MatFormField,
    MatDialogContent
  ],
  templateUrl: './add-station-form.html',
  styleUrl: './add-station-form.component.css'
})
export class AddStationFormComponent {
  constructor(
    public dialogRef: MatDialogRef<AddStationFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data:{ stationNew: StationModel, stations: StationModel[] },
  ) {}

  // Bei OK, Daten an dialogRef übergeben und Form schließen
  onOk(): void {
    this.dialogRef.close(this.data.stationNew);
  }

  // Bei Abbruch Form schließen
  onCancel(): void {
    this.dialogRef.close();
  }

  public setStationName(name: string): void {

    // Bereits vorhandene Nummern abfangen
    this.data.stationNew.name = name;
  }

  public setStationNumber(numberStr: string): void {
    this.data.stationNew.number = Number(numberStr);
  }

  public setStationLocationImage(imageStr: string): void {
    // String zu enum casten
    this.data.stationNew.location.image = FloorPlanImage[imageStr as keyof typeof FloorPlanImage]
  }

  public setStationLocationXcoordinate(x: string): void {
    this.data.stationNew.location.xcoordinate = Number(x);
  }

  public setStationLocationYcoordinate(y: string): void {
    this.data.stationNew.location.ycoordinate = Number(y);
  }

}
