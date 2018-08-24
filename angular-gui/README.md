# Pact JS Angular front end consumer example

This project contains an example on how [Pact JS](https://github.com/pact-foundation/pact-js) can be used for testing your [Angular Services](https://angular.io/tutorial/toh-pt4) that encapsulate REST/Ajax calls to server endpoints. Because most of the files were only generated for a Angular base project, here are the ones you should have a look at: 

## Config
* [package.json](package.json): contains besides all angular dependencies all dev dependencies required for making Pact JS work:
  * @pact-foundation/karma-pact: the karma-pact plugin, that is need for starting the pact mock server(s) before your Karma test run
  * pact: Pact JS the core framework
  * pact-web: Pact Web is an additional module needed if you do not only want to run Pact JS with node but instead in the browser (like here with Karma)
* [karma.conf.js](karma.conf.js): contains the configuration that starts the pact mock server(s) and the required proxy settings to make it work. The sections relevant for the Pact setup are emphasized with comments. 

## Tests
Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).
* [pacts/*](pacts/): contains all JSON Pact files that are saved by the Pact mock server
* [user.service.ts](src/app/user.service.ts): contains the UserService under test
* [user.service.pact.spec.ts](src/app/user.service.pact.spec.ts) is the **actual Pact test**, that specifies the expected interactions against the mock server 
* [user.service.mock.spec.ts](src/app/user.service.mock.spec.ts) is how the Pact test would traditionally be done with Angular's [HttpClientTestingModule](https://angular.io/guide/http#testing-http-requests)
* [pact.test.setup.ts](src/app/pact.test.setup.ts): contains some test helper stuff to make the actual Pact tests more crisp.

