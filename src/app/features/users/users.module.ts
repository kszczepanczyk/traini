import { ModuleWithProviders, NgModule } from '@angular/core';
import { UserRoutingModule } from './users-routing.module';
import { ListComponent } from './components/list/list.component';
import { ClientProfileComponent } from './components/client-profile/client-profile.component';
import { ClientEditComponent } from './components/client-edit/client-edit.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { SharedModule } from 'src/app/shared/shared.module';
import { ProgressComponent } from './components/progress/progress.component';
import { ProgressAddComponent } from './components/progress-add/progress-add.component';
import { NgxMaskDirective, NgxMaskPipe, provideNgxMask } from 'ngx-mask';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { ChartComponent } from 'src/app/shared/chart/chart.component';

@NgModule({
  declarations: [
    ListComponent,
    ClientProfileComponent,
    ClientEditComponent,
    ProgressComponent,
    ProgressAddComponent,
  ],
  imports: [
    UserRoutingModule,
    ReactiveFormsModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    SharedModule,
    NgxMaskDirective,
    NgxMaskPipe,
    MatDatepickerModule,
  ],
  providers: [provideNgxMask()],
})
export class UserModule {
  public static forRoot(): ModuleWithProviders<UserModule> {
    return {
      ngModule: UserModule,
      providers: [],
    };
  }
}
