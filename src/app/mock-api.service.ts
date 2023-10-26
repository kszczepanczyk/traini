// mock-api.service.ts
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import * as dayjs from 'dayjs';
import { Training } from './models/training.model';
import { Trainer, Client } from './models/user.model';

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
          details: 'Short descr',
        },
        {
          date: dayjs().format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Andrzej Anonimowy',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
        },
        {
          date: dayjs().format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Andrzej Anonimowy',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
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
          details: 'Short descr',
        },
        {
          date: dayjs().add(1, 'day').format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Czarek',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
        },
        {
          date: dayjs().add(1, 'day').format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Czarek',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
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
          details: 'Short descr',
        },
        {
          date: dayjs().add(2, 'day').format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Asia Czwartkowa',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
        },
        {
          date: dayjs().add(2, 'day').format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Asia Czwartkowa',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
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
          details: 'Short descr',
        },
        {
          date: dayjs().add(3, 'day').format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Stefan Nowak',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
        },
        {
          date: dayjs().add(3, 'day').format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Stefan Nowak',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
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
          details: 'Short descr',
        },
        {
          date: dayjs().add(4, 'day').format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Witek',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
        },
        {
          date: dayjs().add(4, 'day').format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Witek',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
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
          details: 'Short descr',
        },
        {
          date: dayjs().add(5, 'day').format('DD.MM.YYYY'),
          timeFrom: '13:30',
          timeTo: '14:30',
          client: 'Robert Nowakowski',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
        },
        {
          date: dayjs().add(5, 'day').format('DD.MM.YYYY'),
          timeFrom: '21:30',
          timeTo: '23:30',
          client: 'Robert Nowakowski',
          place: 'Siłownia Cityfit',
          type: 'Trening siłowy',
          details: 'Short descr',
        },
      ]);
    } else {
      return of();
    }
  }
  getUserInfo(): Observable<Trainer> {
    return of({
      id: 1,
      photoUrl: '../../../assets/person.jpg',
      name: 'Andrzej',
      surname: 'Anonimowy',
      email: 'andrzej.anonimowy@gmail.com',
      description:
        'Krótki opis dolor sit amet, consectetur adipiscing elit, sed do eiusmo tempor incididunt ut labore et dolore magna aliqua.',
      phone: '+48 590 584 764',
      city: 'Gliwice',
      gender: 'male',
      tags: [
        { id: 0, name: 'Kondycja' },
        { id: 1, name: 'Urazy' },
        { id: 2, name: 'Trening siłowy' },
      ],
      localizations: [
        { id: 0, name: 'Smartgym Politechnika' },
        { id: 1, name: 'Smartgym Kolberga' },
        { id: 2, name: 'Cityfit Gliwice' },
      ],
    });
  }

  getClients(): Observable<Client[]> {
    return of([
      {
        id: 0,
        photoUrl: '../../../assets/person.jpg',
        name: 'Piotrek',
        surname: 'Klientowy',
        email: 'piotrek.klientowy@gmail.com',
        description:
          'Krótki opis dolor sit amet, consectetur adipiscing elit, sed do eiusmo tempor incididunt ut labore et dolore magna aliqua.',
        phone: '+48 590 584 764',
        city: 'Katowice',
        gender: 'male',
        trainings: [
          {
            date: dayjs().add(5, 'day').format('DD.MM.YYYY'),
            timeFrom: '21:30',
            timeTo: '23:30',
            client: 'Piotrek Klientowy',
            place: 'Siłownia Cityfit',
            type: 'Trening siłowy',
            details: 'Short descr',
          },
          {
            date: dayjs().add(5, 'day').format('DD.MM.YYYY'),
            timeFrom: '11:30',
            timeTo: '13:30',
            client: 'Piotrek Klientowy',
            place: 'Siłownia Cityfit',
            type: 'Trening siłowy',
            details: 'Short descr',
          },
        ],
        progresses: [
          {
            name: 'Deadlift',
            date: '23.10.2023',
            value: 130,
            unit: 'kg',
            tendency: true,
          },
          {
            name: 'Deadlift',
            date: '23.12.2023',
            value: 150,
            unit: 'kg',
            tendency: true,
          },
          {
            name: 'Bench press',
            date: '23.12.2023',
            value: 150,
            unit: 'kg',
            tendency: true,
          },
        ],
      },
      {
        id: 2,
        photoUrl: '../../../assets/person.jpg',
        name: 'Ania',
        surname: 'Klientowa',
        email: 'ania.klientowa@gmail.com',
        description:
          'Krótki opis dolor sit amet, consectetur adipiscing elit, sed do eiusmo tempor incididunt ut labore et dolore magna aliqua.',
        phone: '+48 590 584 764',
        city: 'Katowice',
        gender: 'male',
        trainings: [
          {
            date: dayjs().add(5, 'day').format('DD.MM.YYYY'),
            timeFrom: '21:30',
            timeTo: '23:30',
            client: 'Ania Klientowa',
            place: 'Siłownia Cityfit',
            type: 'Trening siłowy',
            details: 'Short descr',
          },
          {
            date: dayjs().subtract(5, 'day').format('DD.MM.YYYY'),
            timeFrom: '11:30',
            timeTo: '13:30',
            client: 'Ania Klientowa',
            place: 'Siłownia Cityfit',
            type: 'Trening siłowy',
            details: 'Short descr',
          },
        ],
        progresses: [
          {
            name: 'Deadlift',
            date: '23.10.2023',
            value: 130,
            unit: 'kg',
            tendency: true,
          },
          {
            name: 'Deadlift',
            date: '23.12.2023',
            value: 150,
            unit: 'kg',
            tendency: true,
          },
          {
            name: 'Bench press',
            date: '23.12.2023',
            value: 150,
            unit: 'kg',
            tendency: true,
          },
        ],
      },
    ]);
  }
}
