import { Component } from '@angular/core';
import { Router } from '@angular/router';
import * as dayjs from 'dayjs';
import 'dayjs/locale/pl';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor(private router: Router) {
    dayjs.locale('pl');
  }
  title = 'traini';

  isRouteActive(route: string): boolean {
    return this.router.url === '/' + route;
  }

  navigateToSite(route: string) {
    const routeNew: string = '/' + route;
    this.router.navigate([routeNew]);
  }
}
