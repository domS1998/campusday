import { Component } from '@angular/core';
import {CommonModule, NgForOf} from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-image-card',
  templateUrl: './image-card.component.html',
  styleUrl: './image-card.component.css',
  imports: [
    NgForOf,
    CommonModule,
  ]
})
export class ImageCardComponent {
  floorLabels: string[] = ['G2-EG', 'G2-OG1', 'G2-OG2'];
}
