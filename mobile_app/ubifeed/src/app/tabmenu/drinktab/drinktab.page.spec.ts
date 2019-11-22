import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DrinktabPage } from './drinktab.page';

describe('DrinktabPage', () => {
  let component: DrinktabPage;
  let fixture: ComponentFixture<DrinktabPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DrinktabPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DrinktabPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
