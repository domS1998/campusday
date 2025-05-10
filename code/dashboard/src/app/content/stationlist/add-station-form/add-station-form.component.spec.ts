import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddStationFormComponent } from './add-station-form.component';

describe('InputDialogComponent', () => {
  let component: AddStationFormComponent;
  let fixture: ComponentFixture<AddStationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddStationFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddStationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
