import { ModuleWithProviders, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserRoutingModule } from './users-routing.module';
import { ListComponent } from './components/list/list.component';
import { ClientProfileComponent } from './components/client-profile/client-profile.component';

@NgModule({
  declarations: [ListComponent, ClientProfileComponent],
  imports: [UserRoutingModule, CommonModule],
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
