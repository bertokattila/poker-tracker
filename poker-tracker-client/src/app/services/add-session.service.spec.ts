import { TestBed } from '@angular/core/testing';

import { AddSessionService } from './add-session.service';

describe('AddSessionService', () => {
  let service: AddSessionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AddSessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
