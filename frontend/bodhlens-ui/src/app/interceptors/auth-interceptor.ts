import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  console.log("interceptor called");

  const authService = inject(AuthService);

  const token = authService.getToken();

  const router = inject(Router);

  const request = token ? req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`
    }
  }) : req;

  return next(request).pipe(
    catchError((error: HttpErrorResponse) => {

      console.log(error.status);
      console.log(error);

      if(error.status === 401 || error.status === 403) {

        authService.logout();

        router.navigate(['/login']);
      }

      return throwError(() => error);
    })
  );
};
