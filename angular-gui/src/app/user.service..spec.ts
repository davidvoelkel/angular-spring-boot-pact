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
import { XhrFactory } from '@angular/common/http/src/xhr';

const Pact = require('pact-web');

describe('UserService', () => {

    let provider;

    beforeAll((done) => {
        //      client = example.createClient('http://localhost:1234')
        provider = Pact({ consumer: 'hello-client', provider: 'hello-service', web: true });

        // required for slower Travis CI environment
        // setTimeout(function () { done(); }, 2000);

        // Required if run with `singleRun: false`
        provider.removeInteractions();
        done();
    });

    afterAll((done) => {
        console.log('finalize');
        provider.finalize()
                .then(  () => { done(); },
                        (err) => { done.fail(err);
                });
    });

    describe('sayHello', () => {

        beforeAll(function (done) {
            console.log('addInteraction())');
            provider.addInteraction({
                uponReceiving: 'a request for say hello',
                withRequest: {
                    method: 'GET',
                    path: '/user',
                },
                willRespondWith: {
                    status: 200,
                    headers: { 'Content-Type': 'application/json' },
                    body: '{"name": "David Server"}'
                }
            })
            .then(function () {
                console.log('addInteraction() done)');
                done(); 
            }, function (err) { done.fail(err); });
        });

        it('getUser()', async(() =>  {
            const httpClient = new HttpClient(new HttpXhrBackend(new BrowserXhr()));
            const userService = new UserService(httpClient);
            userService.getUser().subscribe((user) => {
                expect(user.name).toBe('David Server');
            });
        }));

         // verify with Pact, and reset expectations
      it('successfully verifies', function(done) {
        provider.verify()
          .then(function(a) {
            done();
          }, function(e) {
            done.fail(e);
          });
      });

    });



});
