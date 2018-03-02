import {HttpClient, HttpXhrBackend} from '@angular/common/http';
import {BrowserXhr} from '@angular/http';
import * as pact from 'pact-web';
import * as Pact from 'pact-web';

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
    this.httpClient = new HttpClient(new HttpXhrBackend(new BrowserXhr()));
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
