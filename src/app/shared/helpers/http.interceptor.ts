import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpInterceptor,
  HttpHandler,
  HttpRequest,
  HTTP_INTERCEPTORS,
  HttpHeaders,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/features/auth/auth.service';
const TOKEN_HEADER_KEY = 'Authorization';
@Injectable()
export class RequestInterceptor implements HttpInterceptor {
  constructor(private _authService: AuthService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    let authReq = request;
    console.log(request);
    const token = this._authService.currentAccessToken;
    if (token != null) {
      authReq = request.clone({
        headers: new HttpHeaders({
          Authorization: `Bearer ${token}`,
        }),
      });
    }
    return next.handle(authReq);
  }
}
