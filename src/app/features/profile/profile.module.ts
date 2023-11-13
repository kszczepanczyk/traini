import { ModuleWithProviders, NgModule } from '@angular/core';
import { ProfileRoutingModule } from './profile-routing.module';
import { ProfileComponent } from './components/profile/profile.component';
import { EditComponent } from './components/edit/edit.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
  declarations: [ProfileComponent, EditComponent],
  imports: [
    ProfileRoutingModule,
    ReactiveFormsModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    SharedModule,
  ],
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
