import { Component } from '@angular/core';
import * as dayjs from 'dayjs';
import 'dayjs/locale/pl';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor() {
    dayjs.locale('pl');
  }
  title = 'traini';
}
