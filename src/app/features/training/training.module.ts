import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { TrainingRoutingModule } from './training-routing.module';
import { AddTrainingComponent } from './add-training/add-training.component';
import { TrainingComponent } from './training/training.component';
import { EditTrainingComponent } from './edit-training/edit-training.component';

@NgModule({
  declarations: [
    AddTrainingComponent,
    TrainingComponent,
    EditTrainingComponent,
  ],
  imports: [TrainingRoutingModule, CommonModule, ReactiveFormsModule],
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
