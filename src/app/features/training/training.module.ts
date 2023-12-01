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
import { MatCheckboxModule } from '@angular/material/checkbox';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [
    AddTrainingComponent,
    TrainingComponent,
    EditTrainingComponent,
  ],
  imports: [
    TrainingRoutingModule,
    SharedModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatDatepickerModule,
    MatNativeDateModule,
    BrowserAnimationsModule,
    NgxMaterialTimepickerModule,
    MatCheckboxModule,
    MatIconModule,
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
