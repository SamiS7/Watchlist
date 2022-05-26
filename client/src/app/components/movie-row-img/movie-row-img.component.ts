import { Component, OnInit, Input } from '@angular/core';
import { MovieImg } from 'src/app/MovieImg';

@Component({
  selector: 'app-movie-row-img',
  templateUrl: './movie-row-img.component.html',
  styleUrls: ['./movie-row-img.component.scss']
})
export class MovieRowImgComponent {
  @Input() movieImg: MovieImg;

  constructor() { }
}
