import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth.service';
import { filter, map, take } from 'rxjs';

export const unAuth: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  return true;
  // return authService.isAuthenticated.pipe(
  //   filter((val) => val !== null),
  //   take(1),
  //   map((isAuth) => {
  //     if (isAuth) {
  //       return false;
  //     } else {
  //       router.navigate(['']);
  //       return true;
  //     }
  //   })
  // );
  // const isAuthenticated = authService.isAuthenticated.value;
  // console.log(isAuthenticated);
  // if (!isAuthenticated) {
  //   return true;
  // }
  // router.navigate(['/']);
  // return false;
};
