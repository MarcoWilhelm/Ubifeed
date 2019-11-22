import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FoodtabPage } from './foodtab.page';

describe('FoodtabPage', () => {
  let component: FoodtabPage;
  let fixture: ComponentFixture<FoodtabPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FoodtabPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FoodtabPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
