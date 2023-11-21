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
    return this._http
      .post(environment.apiKey + '/auth/login', {
        username: credentials.username,
        password: credentials.password,
      })
      .pipe(
        tap((response: { token: string }) => {
          this.currentAccessToken = response.token;
          const storeAccess = Storage.set({
            key: ACCESS_TOKEN_KEY,
            value: response.token,
          });

          storeAccess.catch((error) => {
            return throwError(() => error);
          });
          this.isAuthenticated.next(true);
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

  logout() {
    Storage.remove({ key: ACCESS_TOKEN_KEY })
      .then(() => {
        console.log('Token removed from storage');
        this.isAuthenticated.next(false);
        this._router.navigate(['/login']);
      })
      .catch((error) => {
        console.error('Error removing token from storage:', error);
      });
  }
}
