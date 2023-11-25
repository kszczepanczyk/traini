import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, from, tap, throwError } from 'rxjs';
import { environment } from 'src/environment';
import { Preferences } from '@capacitor/preferences';
import { BehaviorSubject } from 'rxjs';
import { Router } from '@angular/router';
import { HttpResponse } from '@capacitor-community/http';
import { CapacitorHttp } from '@capacitor/core';
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
    const token = await Preferences.get({ key: ACCESS_TOKEN_KEY });
    if (token && token.value && token.value !== 'undefined') {
      this.currentAccessToken = token.value;
      this.isAuthenticated.next(true);
      console.log(this.isAuthenticated.value);
    } else {
      this.isAuthenticated.next(false);
    }
  }

  login(credentials: { username: string; password: string }): Observable<any> {
    const options = {
      url: environment.apiKey + '/auth/login',
      headers: {
        'Content-Type': 'application/json',
      },
      data: {
        username: credentials.username,
        password: credentials.password,
      },
    };

    return from(CapacitorHttp.post(options)).pipe(
      tap((response: HttpResponse) => {
        if (response.status === 403) {
          throw new Error('Forbidden');
        }
        this.currentAccessToken = response.data.token;
        const storeAccess = Preferences.set({
          key: ACCESS_TOKEN_KEY,
          value: response.data.token,
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
    delete registerFields.confirmPassword;
    const options = {
      url: environment.apiKey + '/auth/register',
      headers: {
        'Content-Type': 'application/json',
      },
      data: { ...registerFields },
    };
    return from(CapacitorHttp.post(options));
  }

  logout() {
    Preferences.remove({ key: ACCESS_TOKEN_KEY })
      .then(() => {
        this.isAuthenticated.next(false);
        this._router.navigate(['/login']);
      })
      .catch((error) => {
        console.error('Error removing token from storage:', error);
      });
  }
}
