import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { TrainingRoutingModule } from './training-routing.module';
import { AddTrainingComponent } from './add-training/add-training.component';
import { TrainingComponent } from './training/training.component';
import { EditTrainingComponent } from './edit-training/edit-training.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatNativeDateModule } from '@angular/material/core';
import { NgxMaterialTimepickerModule } from 'ngx-material-timepicker';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AddTrainingComponent,
    TrainingComponent,
    EditTrainingComponent,
  ],
  imports: [
    TrainingRoutingModule,
    CommonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    BrowserAnimationsModule,
    NgxMaterialTimepickerModule,
  ],
  providers: [],
})
export class TrainingModule {
  public static forRoot(): ModuleWithProviders<TrainingModule> {
    return {
      ngModule: TrainingModule,
      providers: [],
    };
  }
}
