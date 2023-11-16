import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProgressAddComponent } from './progress-add.component';

describe('ProgressAddComponent', () => {
  let component: ProgressAddComponent;
  let fixture: ComponentFixture<ProgressAddComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProgressAddComponent]
    });
    fixture = TestBed.createComponent(ProgressAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
