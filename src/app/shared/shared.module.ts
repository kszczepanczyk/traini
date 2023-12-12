import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { SpinnerComponent } from './spinner/spinner.component';
import { ChartComponent } from './chart/chart.component';

@NgModule({
  declarations: [SpinnerComponent, ChartComponent],
  imports: [CommonModule],
  exports: [CommonModule, TranslateModule, SpinnerComponent, ChartComponent],
})
export class SharedModule {}
