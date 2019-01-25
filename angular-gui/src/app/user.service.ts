import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';


import 'rxjs/Rx';

@Injectable()
export class UserService {

    constructor(public httpClient: HttpClient) {}

    public getUser(alias: String): Observable<User> {
      const url = '/api/user/' + alias;
      return this.httpClient.get<User>(url);
    }
}

export interface User {
    name: String;
    email: String;
}
