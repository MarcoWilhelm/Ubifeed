import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-restaurants',
  templateUrl: './restaurants.page.html',
  styleUrls: ['./restaurants.page.scss'],
})
export class RestaurantsPage implements OnInit {

  restaurants: any;

  constructor() { }

  ngOnInit() {
    fetch('../../assets/data/restaurants.json').then(res => res.json())
    .then(json => {
      this.restaurants = json;
    });
  }

}
