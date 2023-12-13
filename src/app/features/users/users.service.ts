import { Injectable } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { CapacitorHttp } from '@capacitor/core';
import { Observable, from, tap } from 'rxjs';
import { environment } from 'src/environment';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  token: string = '';
  constructor(private _authService: AuthService) {
    this.token = this._authService.getAccessToken();
  }

  getUserList(): Observable<any> {
    this.token = this._authService.getAccessToken();
    let options = {
      url: environment.apiKey + '/list',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };

    return from(CapacitorHttp.get(options)).pipe(
      tap((res) => {
        res.data.forEach((client) => {
          client.photo = client.photo
            ? client.photo
            : '../../../assets/default-user.jpg';
          client.phone = client.phone
            ? client.phone
            : 'Brak danych kontaktowych';
        });
      })
    );
  }
  getLocations(): Observable<any> {
    this.token = this._authService.getAccessToken();
    let options = {
      url: environment.apiKey + '/locations',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };

    return from(CapacitorHttp.get(options));
  }
  getClient(id) {
    this.token = this._authService.getAccessToken();
    let options = {
      url: environment.apiKey + '/client/' + id,
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };

    return from(CapacitorHttp.get(options)).pipe(
      tap((res) => {
        res.data.photo = res.data.photo
          ? res.data.photo
          : '../../../assets/default-user.jpg';
      })
    );
  }

  addClient(clientForm) {
    this.token = this._authService.getAccessToken();
    let options = {
      url: environment.apiKey + '/list/add',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
      data: { ...clientForm },
    };

    return from(CapacitorHttp.post(options));
  }

  addProgress(progressForm, id) {
    this.token = this._authService.getAccessToken();

    let options = {
      url: environment.apiKey + '/client/' + id + '/progress',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
      data: { ...progressForm },
    };
    return from(CapacitorHttp.post(options));
  }

  getProgressEntity({ idProgress, idUser }) {
    this.token = this._authService.getAccessToken();

    let options = {
      url: environment.apiKey + '/client/' + idUser + '/progress/' + idProgress,
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };
    return from(CapacitorHttp.get(options));
  }

  deleteProgress(id) {
    this.token = this._authService.getAccessToken();
    let options = {
      url: environment.apiKey + '/client/progress/' + id,
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };

    return from(CapacitorHttp.delete(options));
  }

  deleteProgressEntity(id) {
    this.token = this._authService.getAccessToken();
    let options = {
      url: environment.apiKey + '/client/progressEntity/' + id,
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };

    return from(CapacitorHttp.delete(options));
  }
}
