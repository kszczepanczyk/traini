import { Injectable } from '@angular/core';
import { CapacitorHttp } from '@capacitor/core';
import { from } from 'rxjs';
import { environment } from 'src/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class ScheduleService {
  token: string = '';
  constructor(private _authService: AuthService) {
    this.token = this._authService.getAccessToken();
  }

  getSchedule() {
    this.token = this._authService.getAccessToken();

    let options = {
      url: environment.apiKey + '/training/schedule',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };
    return from(CapacitorHttp.get(options));
  }
}
