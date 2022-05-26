import { Component, OnInit, Input } from '@angular/core';
import { MovieImg } from 'src/app/MovieImg';

@Component({
  selector: 'app-movie-row',
  templateUrl: './movie-row.component.html',
  styleUrls: ['./movie-row.component.scss']
})
export class MovieRowComponent {
  @Input() id: string;
  @Input() rowTitle: string;
  @Input() movieImgs: MovieImg[];
  constructor() { }
}
