import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { SharedModule } from 'src/app/shared/shared.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { RequestInterceptor } from 'src/app/shared/helpers/http.interceptor';

@NgModule({
  declarations: [HomeComponent],
  imports: [SharedModule],
})
export class HomeModule {}
