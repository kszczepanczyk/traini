import { Injectable } from '@angular/core';
import { Observable, from } from 'rxjs';
import { CapacitorHttp } from '@capacitor/core';
import { environment } from 'src/environment';

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  constructor() {}

  getTrainingsByDate(date): Observable<any> {
    const options = {
      url: environment.apiKey + '/auth/register',
      headers: {
        'Content-Type': 'application/json',
      },
      data: { date: date },
    };
    return from(CapacitorHttp.get(options));
  }
}
