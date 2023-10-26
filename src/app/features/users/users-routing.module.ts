import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ListComponent } from './components/list/list.component';
import { ClientProfileComponent } from './components/client-profile/client-profile.component';
const routes: Routes = [
  { path: 'clients', component: ListComponent },
  { path: 'clients/:id', component: ClientProfileComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class UserRoutingModule {}
