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

    beforeAll(() => {
        provider = Pact({
            consumer: 'angular-user-service',
            provider: 'rest-user-service',
            web: true
        });

        // required for slower Travis CI environment
        // setTimeout(function () { done(); }, 2000);

        // Required if run with `singleRun: false`
        provider.removeInteractions();
    });

    afterAll(async(() => {
        provider.finalize();
    }));

    describe('getUser()', () => {

        beforeAll(() => {
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
            });
        });

        it('getUser()', async(() =>  {
            const httpClient = new HttpClient(new HttpXhrBackend(new BrowserXhr()));
            const userService = new UserService(httpClient);
            userService.getUser().subscribe((user) => {
                expect(user.name).toBe('David Server');
            });
        }));

      // verify with Pact, and reset expectations
      it('successfully verifies', async(() => {
        return provider.verify();
      }));

    });



});
