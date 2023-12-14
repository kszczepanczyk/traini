import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { ScheduleComponent } from './schedule.component';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  declarations: [ScheduleComponent],
  imports: [SharedModule, MatButtonModule, MatIconModule],
})
export class ScheduleModule {}
