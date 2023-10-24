import { ModuleWithProviders, NgModule } from '@angular/core';
import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileComponent } from './components/profile/profile.component';
import { EditComponent } from './components/edit/edit.component';

@NgModule({
  declarations: [ProfileComponent, EditComponent],
  imports: [ProfileRoutingModule],
  providers: [],
})
export class ProfileModule {
  public static forRoot(): ModuleWithProviders<ProfileModule> {
    return {
      ngModule: ProfileModule,
      providers: [],
    };
  }
}
