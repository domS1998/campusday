import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class QuicksortService {

  quickSortInPlace(arr: number[], left: number = 0, right: number = arr.length - 1): number[] {
    if (left < right) {
      const pivotIndex = this.partition(arr, left, right);
      this.quickSortInPlace(arr, left, pivotIndex - 1);
      this.quickSortInPlace(arr, pivotIndex + 1, right);
    }
    return arr;
  }


  partition(arr: number[], left: number, right: number): number {
    const pivot = arr[right]; // Pick the last element as pivot
    let i = left - 1; // i will mark the end of the smaller-than-pivot region

    for (let j = left; j < right; j++) {
      if (arr[j] < pivot) {
        i++;
        [arr[i], arr[j]] = [arr[j], arr[i]]; // Swap elements
      }
    }

    // Place pivot in the correct position
    [arr[i + 1], arr[right]] = [arr[right], arr[i + 1]];
    return i + 1;
  }
}
