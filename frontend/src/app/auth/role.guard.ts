// src/app/auth/role.guard.ts
import { inject } from '@angular/core';
import { CanActivateFn, ActivatedRouteSnapshot, Router } from '@angular/router';
import { AuthService } from './auth.service';

export const roleGuard: CanActivateFn = (route: ActivatedRouteSnapshot) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const expectedRoles = route.data['roles'] as Array<string>;
  const userRole = authService.getUserRole();

  if (!userRole || !expectedRoles.includes(userRole)) {
    router.navigate(['/dashboard']);
    return false;
  }

  return true;
};
