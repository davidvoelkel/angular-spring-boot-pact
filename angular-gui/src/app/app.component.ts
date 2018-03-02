import { Component } from '@angular/core';
import { UserService } from './user.service';
import { BehaviorSubject, Observable } from 'rxjs';
import { OnInit } from '@angular/core/src/metadata/lifecycle_hooks';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent implements OnInit {
  name: String;
  title = 'app';

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.name = 'initial';
    this.userService.getUser('david79')
                    .subscribe(user => this.name = user.name);
  }


}
