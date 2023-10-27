import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './components/list/list.component';
import { ClientProfileComponent } from './components/client-profile/client-profile.component';
import { ClientEditComponent } from './components/client-edit/client-edit.component';
const routes: Routes = [
  { path: 'clients', component: ListComponent },
  { path: 'clients/:id', component: ClientProfileComponent },
  { path: 'clients/:id/edit', component: ClientEditComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
