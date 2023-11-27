import { NgModule } from '@angular/core';
import { HomeComponent } from './home.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@NgModule({
  declarations: [HomeComponent],
  imports: [SharedModule, MatButtonModule, MatIconModule],
})
export class HomeModule {}
