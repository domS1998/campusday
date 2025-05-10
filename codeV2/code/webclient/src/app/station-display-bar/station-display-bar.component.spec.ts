import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StationDisplayBarComponent } from './station-display-bar.component';

describe('StationDisplayBarComponent', () => {
  let component: StationDisplayBarComponent;
  let fixture: ComponentFixture<StationDisplayBarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StationDisplayBarComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StationDisplayBarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
