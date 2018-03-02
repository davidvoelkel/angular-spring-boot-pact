import { async, TestBed } from '@angular/core/testing';
import { UserService } from './user.service';
import { HttpModule, BrowserXhr } from '@angular/http';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { HttpHandler } from '@angular/common/http';
import { inject } from '@angular/core/testing';
import { fail } from 'assert';
import { JSONPBackend } from '@angular/http';
import { HttpInterceptorHandler } from '@angular/common/http/src/interceptor';
import { HttpXhrBackend } from '@angular/common/http';
import { MockBackend } from '@angular/http/testing';
import { XhrFactory } from '@angular/common/http/src/xhr';

const Pact = require('pact-web');

describe('UserService', () => {

    describe('XXXXXXX', () => {
        beforeAll((done) => {
            // const httpClient = new HttpClient(new HttpXhrBackend(new MockBackend()));
            // const userService = new UserService(httpClient);
            // provider.addInteraction({
            //     uponReceiving: 'a request for say hello',
            //     withRequest: {
            //         method: 'GET',
            //         path: '/user',
            //     },
            //     willRespondWith: {
            //         status: 200,
            //         headers: { 'Content-Type': 'application/json' },
            //         body: '{"name": "David"}'
            //     }
            // }).then(done, done.fail);
            done();
        });
        
        it('YYYYYYY', (done) =>  {
            // const httpClient = new HttpClient(new HttpXhrBackend(new BrowserXhr()));
            // const userService = new UserService(httpClient);
            
            // userService.getUser().subscribe((user) => {
                //     expect(user.name).toBe('David');
                //     // verify with Pact, and reset expectations
                //     return provider.verify()
                //                    .then(done, done.fail);
                // });
            done();
        });
    });
});
