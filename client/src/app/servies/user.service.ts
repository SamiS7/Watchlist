import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  apiUrl: string = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  getUser(userId: number): Observable<User> {
    console.log(`${this.apiUrl}/account/${userId}`);
    return this.http.get<User>(`${this.apiUrl}/account/${userId}`);
  }
}
