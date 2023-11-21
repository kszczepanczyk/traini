import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from './auth.service';
import { filter, take, map } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const isAuthenticated = authService.isAuthenticated.value;
  console.log(isAuthenticated);
  if (isAuthenticated) {
    return true;
  }
  router.navigate(['/login']);
  return false;
};
