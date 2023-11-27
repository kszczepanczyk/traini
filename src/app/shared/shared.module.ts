import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { SpinnerComponent } from './spinner/spinner.component';

@NgModule({
  declarations: [SpinnerComponent],
  imports: [CommonModule],
  exports: [CommonModule, TranslateModule, SpinnerComponent],
})
export class SharedModule {}
