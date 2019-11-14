import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginLoadPage } from './login-load.page';

describe('LoginLoadPage', () => {
  let component: LoginLoadPage;
  let fixture: ComponentFixture<LoginLoadPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoginLoadPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginLoadPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
