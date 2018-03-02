import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import 'rxjs/add/operator/catch';
import 'rxjs/add/observable/throw';
import 'rxjs/Rx';

@Injectable()
export class UserService {

    constructor(public httpClient: HttpClient) {}

    public getUser(alias: String): Observable<User> {
        return this.httpClient.get<User>('/user/' + alias);
    }
}

export class User {
    constructor(public name: String, public email: String) {}
}
