import { Component, OnInit, Input } from '@angular/core';
import { faCircleUser, faBars } from '@fortawesome/free-solid-svg-icons';
import { User } from 'src/app/models/User';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent{
  @Input() user: User;
  faCircleUser = faCircleUser;
  faBars = faBars;

  constructor() { }

  onClick() {
    
  }
}
