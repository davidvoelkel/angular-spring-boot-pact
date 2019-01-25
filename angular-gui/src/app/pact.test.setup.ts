import {HttpClient, HttpHandler, HttpXhrBackend} from '@angular/common/http';
import {BrowserXhr} from '@angular/http';
import * as pact from 'pact-web';
import * as Pact from 'pact-web';
import {Observable} from "rxjs";
import {HttpEvent} from "@angular/common/http/src/response";
import {HttpRequest} from "@angular/common/http/src/request";

export interface IPactTestSetupConfig {
  consumer: string;
  provider: string;
  port: number;
}

export class PactTestSetup {

  private httpClient: HttpClient;
  private provider: pact.PactWebProvider;

  constructor(config: IPactTestSetupConfig) {
    this.provider = Pact({
      consumer: config.consumer,
      provider: config.provider,
      port:     config.port,
    });
    this.httpClient = new HttpClient(new BrowserXhrBackendWithBaseUrl('http://localhost:' + config.port));
  }

  getHttpClient(): HttpClient {
    return this.httpClient;
  }

  beforeAll() {
    return (done) => {
      // Required if run with `singleRun: false`
      this.provider.removeInteractions()
                   .then(done, done.fail);
    };
  }

  afterAll() {
    return (done) => {
      this.provider.finalize()
                   .then(done, done.fail);
    };
  }

  addInteraction(interaction: any, done) {
    this.provider.addInteraction(interaction)
                 .then(done, done.fail);
  }

  verify(done) {
    this.provider.verify()
                 .then(done, done.fail);
  }
}

class BrowserXhrBackendWithBaseUrl implements HttpHandler {

  httpXhrBackend = new HttpXhrBackend(new BrowserXhr());

  constructor(private baseUrl: string) {}

  handle(req: HttpRequest<any>): Observable<HttpEvent<any>> {
    const requestPath = req.url;
    const urlWithBaseUrl = this.baseUrl + this.cutOffLeadingPrefix(requestPath, '/api');

    return this.httpXhrBackend.handle(req.clone({ url: urlWithBaseUrl }));
  }

  private cutOffLeadingPrefix(requestPath: string, prefix: string) {
    return requestPath.substring(prefix.length, requestPath.length);
  }
}
