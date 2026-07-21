import { TestBed } from '@angular/core/testing';

import { ChatSession } from './chat-session';

describe('ChatSession', () => {
  let service: ChatSession;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ChatSession);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
