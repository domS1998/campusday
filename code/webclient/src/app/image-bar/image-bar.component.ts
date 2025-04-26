import { Component } from '@angular/core';
// Komponenten müssen benutzte Komponenten immer importieren, wenn sie standalone sind
// andernfalls müssen sie im gemeinsamen Modul in declarations eingetragen werden
import {ImageCardComponent} from './image-card/image-card.component';

@Component({
  standalone: true,
  selector: 'app-image-bar',
  templateUrl: './image-bar.component.html',
  imports: [
    ImageCardComponent
  ],
  styleUrl: './image-bar.component.css'
})
export class ImageBarComponent {

}
