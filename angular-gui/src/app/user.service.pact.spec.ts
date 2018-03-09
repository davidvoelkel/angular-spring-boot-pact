import {UserService} from './user.service';
import {PactTestSetup} from './pact.test.setup';

describe('UserService', () => {

  const pactSetUp = new PactTestSetup({
    consumer: 'angular-user-service', provider: 'rest-user-service',
    port: 1234,
  });

  beforeAll(pactSetUp.beforeAll);
  afterAll(pactSetUp.afterAll);

  describe('getUser()', () => {

    const userService = new UserService(pactSetUp.getHttpClient())

    beforeAll((done) => {
      pactSetUp.addInteraction({
        uponReceiving: 'GET /user/david79',
        withRequest: {
          method: 'GET',
          path: '/user/david79',
        },
        willRespondWith: {
          status: 200,
          headers: { 'Content-Type': 'application/json' },
          body: {name: 'David', email: 'david@gmail.com' }
        }
      }, done);
    });

    it('calls GET on /user endpoint and delivers a valid user object from the response',
      (done) => {
      userService.getUser('david79')
                 .subscribe((user) => {

        expect(user.name).toBe('David');
        expect(user.email).toBe('david@gmail.com');

        pactSetUp.verify(done);
      });
    });
  });

});
