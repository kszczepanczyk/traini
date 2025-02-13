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
      url: environment.apiKey + '/client/list',
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
      url: environment.apiKey + '/trainer/location',
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
      url: environment.apiKey + '/client',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
      data: { ...clientForm },
    };

    return from(CapacitorHttp.post(options));
  }

  updateClient(clientForm, id) {
    this.token = this._authService.getAccessToken();
    let options = {
      url: environment.apiKey + '/client/' + id,
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
      data: { ...clientForm },
    };

    return from(CapacitorHttp.put(options));
  }

  addProgress(progressForm, id) {
    this.token = this._authService.getAccessToken();

    let options = {
      url: environment.apiKey + '/progress/' + id,
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
      url: environment.apiKey + '/progress/entity/' + idUser + '/' + idProgress,
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
      url: environment.apiKey + '/progress/' + id,
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };

    return from(CapacitorHttp.delete(options));
  }

  deleteUser(id) {
    this.token = this._authService.getAccessToken();
    let options = {
      url: environment.apiKey + '/client',
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
      data: { userId: id },
    };

    return from(CapacitorHttp.delete(options));
  }

  deleteProgressEntity(id) {
    this.token = this._authService.getAccessToken();
    let options = {
      url: environment.apiKey + '/progress/entity/' + id,
      headers: {
        'Content-Type': 'application/json',
        ...(this.token && { Authorization: `Bearer ${this.token}` }),
      },
    };

    return from(CapacitorHttp.delete(options));
  }
}
