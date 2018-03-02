import {UserService} from './user.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {TestBed} from "@angular/core/testing";

describe('UserService Mocktest', () => {

  let httpMock: HttpTestingController;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:   [HttpClientTestingModule],
      providers: [UserService]
    });
    httpMock =    TestBed.get(HttpTestingController);
    userService = TestBed.get(UserService);
  });

  it('getUser()', () => {
      userService.getUser('david79')
                 .subscribe((user) => {
                   expect(user.name).toBe('David');
                   expect(user.email).toBe('david@gmail.com');
                 });
      httpMock.expectOne('/user/david79')
              .flush({'name': 'David', 'email': 'david@gmail.com' });
      httpMock.verify();
    });
});
