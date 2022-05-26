import { Component, OnInit } from '@angular/core';
import { MovieImg } from 'src/app/MovieImg';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  shortlyAdded: MovieImg[];
  seen: MovieImg[];
  notSeen: MovieImg[];
  bestRated: MovieImg[];

  constructor() { }

  ngOnInit(): void {
  }

}
