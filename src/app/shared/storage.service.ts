import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Storage } from '@capacitor/storage';
import { BehaviorSubject } from 'rxjs';
const ACCESS_TOKEN_KEY = 'auth-token';

@Injectable({
  providedIn: 'root',
})
export class StorageService {
  isAuthenticated: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(
    false
  );
  currentAccessToken = '';
  constructor(private router: Router) {
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

  public isLoggedIn(): boolean {
    if (
      this.router.url === '/login' ||
      this.router.url === '/register' ||
      this.router.url === '/reset-password'
    ) {
      return false;
    }
    return true;
    // const user = window.sessionStorage.getItem(USER_KEY);
    // if (user) {
    //   return true;
    // }
    // return false;
  }
}
