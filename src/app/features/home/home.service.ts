import { Injectable } from '@angular/core';
import { Observable, from, tap } from 'rxjs';
import { CapacitorHttp } from '@capacitor/core';
import { environment } from 'src/environment';
import { AuthService } from '../auth/auth.service';
import * as dayjs from 'dayjs';

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  token: string = '';
  constructor(private _authService: AuthService) {
    this.token = this._authService.getAccessToken();
  }

  getTrainingsByDate(date): Observable<any> {
    this.token = this._authService.getAccessToken();
    const options = {
      url: environment.apiKey + '/training/list/' + date,
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };
    return from(CapacitorHttp.get(options)).pipe(
      tap((res) => {
        res.data.forEach((tr) => {
          tr.trainingDate.start = dayjs(tr.trainingDate.start).format('HH:mm');
          tr.trainingDate.end = dayjs(tr.trainingDate.end).format('HH:mm');
        });
      })
    );
  }

  getNameAndAvatar(): Observable<any> {
    this.token = this._authService.getAccessToken();
    let options = {
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
          : '../../../assets/default-user.jpg';
      })
    );
  }
}
