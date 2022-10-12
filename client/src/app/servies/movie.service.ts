import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MovieImg } from '../models/MovieImg';
import { User } from '../models/User';

@Injectable({
  providedIn: 'root'
})
export class MovieService {
  apiUrl: string = 'http://localhost:8080/movie';

  constructor(private http: HttpClient) { }

  getMovieImg(userId: number, categoryId: String): Observable<MovieImg[]> {
    if (categoryId == 'famous') {
      return this.http.get<MovieImg[]>(`${this.apiUrl}/${categoryId}/0/10`);
    }
    return this.http.get<MovieImg[]>(`${this.apiUrl}/${userId}/${categoryId}/0/10`);
  }
}
