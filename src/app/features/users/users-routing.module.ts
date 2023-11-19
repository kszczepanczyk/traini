import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './components/list/list.component';
import { ClientProfileComponent } from './components/client-profile/client-profile.component';
import { ClientEditComponent } from './components/client-edit/client-edit.component';
import { ProgressAddComponent } from './components/progress-add/progress-add.component';
import { ProgressComponent } from './components/progress/progress.component';
const routes: Routes = [
  { path: 'clients', component: ListComponent },
  { path: 'clients/:id', component: ClientProfileComponent },
  { path: 'clients/:id/edit', component: ClientEditComponent },
  { path: 'clients/add', component: ClientEditComponent },
  { path: 'clients/:id/progress/add', component: ProgressAddComponent },
  { path: 'clients/:id/progress/:progress_id', component: ProgressComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
