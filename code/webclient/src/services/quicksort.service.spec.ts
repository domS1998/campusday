import { TestBed } from '@angular/core/testing';

import { QuicksortService } from './quicksort.service';

describe('QuicksortService', () => {
  let service: QuicksortService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QuicksortService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
