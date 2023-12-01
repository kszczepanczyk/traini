import { Injectable } from '@angular/core';
import { CapacitorHttp } from '@capacitor/core';
import { from } from 'rxjs';
import { environment } from 'src/environment';
import { AuthService } from '../auth/auth.service';

@Injectable({
  providedIn: 'root',
})
export class TrainingService {
  token: string = '';
  constructor(private _authService: AuthService) {
    this.token = this._authService.getAccessToken();
  }

  addTraining(trainingForm) {
    this.token = this._authService.getAccessToken();
    let options = {
      url: environment.apiKey + '/training',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
      data: { ...trainingForm },
    };

    return from(CapacitorHttp.post(options));
  }
}
