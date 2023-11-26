import { Injectable } from '@angular/core';
import { Observable, from } from 'rxjs';
import { CapacitorHttp } from '@capacitor/core';
import { environment } from 'src/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  token: string = '';
  constructor(private _authService: AuthService) {
    this.token = this._authService.currentAccessToken;
  }

  getTrainingsByDate(date): Observable<any> {
    const options = {
      url: environment.apiKey + '/home/trainings/' + date,
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };
    return from(CapacitorHttp.get(options));
  }

  getNameAndAvatar(): Observable<any> {
    let options = {
      url: environment.apiKey + '/home/avatar',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };

    return from(CapacitorHttp.get(options));
  }
}
