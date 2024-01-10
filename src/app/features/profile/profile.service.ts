import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Observable, from, tap } from 'rxjs';
import { CapacitorHttp } from '@capacitor/core';
import { environment } from 'src/environment';

@Injectable({
  providedIn: 'root',
})
export class ProfileService {
  token: string = '';
  constructor(private _authService: AuthService) {
    this.token = this._authService.getAccessToken();
  }

  getProfile(): Observable<any> {
    this.token = this._authService.getAccessToken();
    const options = {
      url: environment.apiKey + '/trainer',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };
    return from(CapacitorHttp.get(options)).pipe(
      tap((res) => {
        res.data.photoB64 = res.data.photoB64
          ? res.data.photoB64
          : '../../../../../assets/default-user.jpg';
      })
    );
  }

  modifyProfile(userFields): Observable<any> {
    this.token = this._authService.getAccessToken();
    const options = {
      url: environment.apiKey + '/trainer',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
      data: { ...userFields },
    };
    return from(CapacitorHttp.put(options));
  }
}
