import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { SpinnerComponent } from './spinner/spinner.component';
import { ChartComponent } from './chart/chart.component';
import { ErrorPageComponent } from './error-page/error-page.component';

@NgModule({
  declarations: [SpinnerComponent, ChartComponent, ErrorPageComponent],
  imports: [CommonModule],
  exports: [
    CommonModule,
    TranslateModule,
    SpinnerComponent,
    ChartComponent,
    ErrorPageComponent,
  ],
})
export class SharedModule {}
