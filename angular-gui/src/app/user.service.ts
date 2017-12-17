import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/Rx';

@Injectable()
export class UserService {

    constructor(public http: HttpClient) {}

    public getUser(): Observable<User> {
        return this.http.get<User>('/api/user')
        .catch(error => {
            console.log(error);
            return Observable.of(new User(error));
         });
    }
}

export class User {
    constructor(public name: String) {}
}
