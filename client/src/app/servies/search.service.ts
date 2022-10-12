import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ImdbMovie } from '../models/ImdbMovie';

@Injectable({
  providedIn: 'root'
})
export class SearchService {
  apiUrl: string = 'http://localhost:8080/movie';

  constructor() { }

  getResult(): Observable<ImdbMovie> {
    return null;
  }
}
