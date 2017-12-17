import { TestBed, async } from '@angular/core/testing';
import { AppComponent } from './app.component';
import { UserService, User } from './user.service';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';

describe('AppComponent 2', () => {

  const userServiceMock = {
    getUser: jasmine.createSpy('getUser')
  };

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [
        AppComponent
      ],
      providers: [
        { provide: UserService, useValue: userServiceMock },
        { provide: HttpClient, useValue: {} }
      ]
    }).compileComponents();
  }));

  it('gets user from UserService', async(() => {
    userServiceMock.getUser.and.returnValue(Observable.create((observer) => {
      observer.next(new User('David Völkel'));
    }));
    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.debugElement.nativeElement;
    expect(compiled.querySelector('h1').textContent)
                   .toContain('Welcome David Völkel to app!');
  }));
});
