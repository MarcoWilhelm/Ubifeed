import { TestBed } from '@angular/core/testing';

import { TabserviceService } from './tabservice.service';

describe('TabserviceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: TabserviceService = TestBed.get(TabserviceService);
    expect(service).toBeTruthy();
  });
});
