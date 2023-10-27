import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserRoutingModule } from './users-routing.module';
import { ListComponent } from './components/list/list.component';
import { ClientProfileComponent } from './components/client-profile/client-profile.component';
import { ClientEditComponent } from './components/client-edit/client-edit.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [ListComponent, ClientProfileComponent, ClientEditComponent],
  imports: [UserRoutingModule, CommonModule, ReactiveFormsModule],
  providers: [],
})
export class UserModule {
  public static forRoot(): ModuleWithProviders<UserModule> {
    return {
      ngModule: UserModule,
      providers: [],
    };
  }
}
