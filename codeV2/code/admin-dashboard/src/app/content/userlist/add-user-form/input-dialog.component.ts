import { Component } from '@angular/core';
import { Inject } from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogActions, MatDialogContent, MatDialogRef} from '@angular/material/dialog';
import {FormsModule} from '@angular/forms';
import {MatFormField, MatLabel} from '@angular/material/input';

@Component({
  selector: 'app-add-user-form',
  imports: [
    FormsModule,
    MatDialogActions,
    MatFormField,
    MatDialogContent
  ],
  templateUrl: './input-dialog.component.html',
  styleUrl: './input-dialog.component.css'
})
export class InputDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<InputDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { username: string; cardId: string }
  ) {}

  // Bei OK, Daten an dialogRef übergeben und Form schließen
  onOk(): void {
    this.dialogRef.close(this.data);
  }

  // Bei Abbruch Form schließen
  onCancel(): void {
    this.dialogRef.close();
  }
}
