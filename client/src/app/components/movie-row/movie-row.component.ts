import { Component, OnInit, Input } from '@angular/core';
import { MovieImg } from 'src/app/models/MovieImg';
import { User } from 'src/app/models/User';
import { MovieService } from 'src/app/servies/movie.service';

@Component({
  selector: 'app-movie-row',
  templateUrl: './movie-row.component.html',
  styleUrls: ['./movie-row.component.scss']
})
export class MovieRowComponent implements OnInit {
  @Input() id: string;
  @Input() rowTitle: string;
  @Input() user: User;
  movieImgs: MovieImg[];
  constructor(private movieService: MovieService) { }

  ngOnInit(): void {
    setTimeout(() => {
      if (this.id == 'famous') {
        this.movieService.getMovieImg(-1, this.id).subscribe(m => this.movieImgs = m);
      } else {
        this.movieService.getMovieImg(this.user.id, this.id).subscribe(m => this.movieImgs = m);
      }
    }, 1000);
  }
}
