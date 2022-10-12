import { Component, OnInit } from '@angular/core';
import { MovieImg } from 'src/app/models/MovieImg';
import { User } from 'src/app/models/User';
import { MovieService } from 'src/app/servies/movie.service';
import { UserService } from 'src/app/servies/user.service';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  user: User;

  constructor(private userSerivce: UserService) { }

  ngOnInit(): void {
    // let yo = async () => {

    //   let f = async () => {
    //     this.userSerivce.getUser(1).subscribe(u => {
    //       console.log(u);
    //       this.user = u;
    //     });
    //   }

    //   await f().then(
    //     (values) => {
    //       console.log(values)
    //       this.movieService.getShortlyAdded(this.user.id).subscribe(m => this.shortlyAdded = m)
    //       this.movieService.getSeen(this.user.id).subscribe(m => this.seen = m)
    //       this.movieService.getNotSeen(this.user.id).subscribe(m => this.notSeen = m)
    //       this.movieService.getBestRated(this.user.id).subscribe(m => this.bestRated = m)
    //     }
    //   )
    // }
    // yo();

    this.userSerivce.getUser(1).subscribe(u => {
      this.user = u;
    });
  }
}
