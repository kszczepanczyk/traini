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
    let options = {
      url: environment.apiKey + '/home/avatar',
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
