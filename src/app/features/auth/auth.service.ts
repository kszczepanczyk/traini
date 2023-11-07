import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environment';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(environment + '/login', {
      username,
      password,
    });
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(environment + 'register', {
      username,
      email,
      password,
    });
  }

  logout(): Observable<any> {
    return this.http.post(environment + 'logout', {});
  }
}
