import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateStationFormComponent } from './update-station-form.component';

describe('UpdateStationFormComponent', () => {
  let component: UpdateStationFormComponent;
  let fixture: ComponentFixture<UpdateStationFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateStationFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateStationFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
