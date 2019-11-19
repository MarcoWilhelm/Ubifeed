import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.page.html',
  styleUrls: ['./menu.page.scss'],
})
export class MenuPage implements OnInit {

  public pages = [
    {
      title: 'Make Order',
      url: '/menu/venues',
      icon: 'pizza'
    },
    {
      title: 'Your Orders',
      url: '/menu/venues',
      icon: 'list'
    },
    {
      title: 'Profile',
      url: '/menu/profile',
      icon: 'person'
    }
  ]

  constructor() { }

  ngOnInit() {
  }

}
