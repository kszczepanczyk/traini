import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import * as dayjs from 'dayjs';
import 'dayjs/locale/pl';
import { AuthService } from './features/auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  constructor(
    private router: Router,
    private _authService: AuthService,
    private _activeRoute: ActivatedRoute
  ) {
    dayjs.locale('pl');
  }
  title = 'traini';

  isRouteActive(route: string): boolean {
    return this.router.url === '/' + route;
  }

  isLoggedIn(): boolean {
    if (
      this.router.url === '/login' ||
      this.router.url === '/login?registered=true' ||
      this.router.url === '/register' ||
      this.router.url === '/forgot'
    )
      return false;
    return true;
  }

  navigateToSite(route: string) {
    const routeNew: string = '/' + route;
    this.router.navigate([routeNew]);
  }
}
