import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-restaurants',
  templateUrl: './restaurants.page.html',
  styleUrls: ['./restaurants.page.scss'],
})
export class RestaurantsPage implements OnInit {

  allRestaurants: Array<any>;
  restaurants: Array<any>;
  venueId: any;

  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.venueId = this.activatedRoute.snapshot.paramMap.get('id');
    fetch('../../assets/data/restaurants.json').then(res => res.json())
    .then(res => {
      this.allRestaurants = res;
      console.log(this.allRestaurants);

      this.restaurants = this.allRestaurants.filter((restaurant) => {
        return restaurant.venue_id == this.venueId;
      });
      console.log(this.venueId);
      console.log(this.restaurants);

    });

  }

}
