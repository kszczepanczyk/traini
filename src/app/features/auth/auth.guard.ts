import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';
import { filter, take, map } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  return authService.isAuthenticated.pipe(
    filter((val) => val !== null),
    take(1),
    map((isAuth) => {
      if (isAuth) {
        return true;
      } else {
        router.navigate(['/login']);
        return false;
      }
    })
  );
  // const isAuthenticated = authService.isAuthenticated;
  // console.log(isAuthenticated);
  // if (isAuthenticated) {
  //   return true;
  // }
  // router.navigate(['/login']);
  // return false;
};
