import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TabmenuPage } from './tabmenu.page';

describe('TabmenuPage', () => {
  let component: TabmenuPage;
  let fixture: ComponentFixture<TabmenuPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TabmenuPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TabmenuPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
