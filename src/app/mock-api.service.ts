// mock-api.service.ts
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import * as dayjs from 'dayjs';
import { Training } from './models/training.model';

@Injectable({
  providedIn: 'root',
})
export class MockApiService {
  getTrainings(date: any): Observable<Training[]> {
    if (date === dayjs().format('D')) {
      return of([
        {
          date: dayjs().format('DD.MM.YYYY'),
          timeFrom: '12:30',
          timeTo: '13:30',
          client: 'Andrzej Anonimowy',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Andrzej Anonimowy',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Andrzej Anonimowy',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
      ]);
    } else if (date === dayjs().add(1, 'day').format('D')) {
      return of([
        {
          date: dayjs().add(1, 'day').format('DD.MM.YYYY'),
          timeFrom: '12:30',
          timeTo: '13:30',
          client: 'Czarek',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().add(1, 'day').format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Czarek',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().add(1, 'day').format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Czarek',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
      ]);
    } else if (date === dayjs().add(2, 'day').format('D')) {
      return of([
        {
          date: dayjs().add(2, 'day').format('DD.MM.YYYY'),
          timeFrom: '12:30',
          timeTo: '13:30',
          client: 'Asia Czwartkowa',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().add(2, 'day').format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Asia Czwartkowa',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().add(2, 'day').format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Asia Czwartkowa',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
      ]);
    } else if (date === dayjs().add(3, 'day').format('D')) {
      return of([
        {
          date: dayjs().add(3, 'day').format('DD.MM.YYYY'),
          timeFrom: '12:30',
          timeTo: '13:30',
          client: 'Stefan Nowak',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().add(3, 'day').format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Stefan Nowak',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().add(3, 'day').format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Stefan Nowak',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
      ]);
    } else if (date === dayjs().add(4, 'day').format('D')) {
      return of([
        {
          date: dayjs().add(4, 'day').format('DD.MM.YYYY'),
          timeFrom: '12:30',
          timeTo: '13:30',
          client: 'Witek',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().add(4, 'day').format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Witek',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().add(4, 'day').format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Witek',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
      ]);
    } else if (date === dayjs().add(5, 'day').format('D')) {
      return of([
        {
          date: dayjs().add(5, 'day').format('DD.MM.YYYY'),
          timeFrom: '12:30',
          timeTo: '13:30',
          client: 'Robert Nowakowski',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().add(5, 'day').format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Robert Nowakowski',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
        {
          date: dayjs().add(5, 'day').format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Robert Nowakowski',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
        },
      ]);
    } else {
      return of();
    }
  }
}
