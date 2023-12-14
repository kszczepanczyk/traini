import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-spinner-small',
  templateUrl: './spinner-small.component.html',
  styleUrls: ['./spinner-small.component.scss'],
})
export class SpinnerSmallComponent {
  @Input({ required: true }) dataLoaded: boolean = true;
}
