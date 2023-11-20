import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddTrainingComponent } from './add-training/add-training.component';
import { EditTrainingComponent } from './edit-training/edit-training.component';
import { TrainingComponent } from './training/training.component';
import { authGuard } from '../auth/auth.guard';

const routes: Routes = [
  {
    path: '',
    canActivateChild: [authGuard],
    children: [
      { path: 'training/add', component: AddTrainingComponent },
      { path: 'training/:id', component: TrainingComponent },
      { path: 'training/:id/edit', component: EditTrainingComponent },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TrainingRoutingModule {}
