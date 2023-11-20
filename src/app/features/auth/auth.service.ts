import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, catchError, from, switchMap, tap, throwError } from 'rxjs';
import { environment } from 'src/environment';
import { Storage } from '@capacitor/storage';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
const ACCESS_TOKEN_KEY = 'auth-token';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  isAuthenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(
    null
  );
  currentAccessToken = '';
  constructor(private _router: Router, private _http: HttpClient) {
    this.loadToken();
  }

  async loadToken() {
    const token = await Storage.get({ key: ACCESS_TOKEN_KEY });
    if (token && token.value) {
      this.currentAccessToken = token.value;
      this.isAuthenticated.next(true);
    } else {
      this.isAuthenticated.next(false);
    }
  }

  login(credentials: { username: string; password: string }): Observable<any> {
    console.log(this.isAuthenticated);
    return this._http
      .post(environment.apiKey + '/auth/login', {
        credentials,
      })
      .pipe(
        tap((response) => {
          if (typeof response === 'string') {
            this.currentAccessToken = response;

            const storeAccess = Storage.set({
              key: ACCESS_TOKEN_KEY,
              value: response,
            });

            storeAccess.catch((error) => {
              return throwError(() => error);
            });
          }
        }),
        tap((_) => {
          this.isAuthenticated.next(true);
          console.log(this.isAuthenticated);
        }),
        catchError((error: any) => {
          return throwError(() => error);
        })
      );
  }

  register(registerFields): Observable<any> {
    return this._http.post(environment.apiKey + '/auth/register', {
      registerFields,
    });
  }

  logout(): Observable<any> {
    return this._http.post(environment + 'logout', {});
  }
}
