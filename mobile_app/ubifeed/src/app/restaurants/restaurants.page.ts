import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LocalstorageService } from '../services/localstorage.service';
import { HttpClient, HttpParams, HttpHandler, HttpHeaders } from '@angular/common/http';
import { RestaurantService } from '../services/restaurant.service';


@Component({
  selector: 'app-restaurants',
  templateUrl: './restaurants.page.html',
  styleUrls: ['./restaurants.page.scss'],
})
export class RestaurantsPage implements OnInit {

  // allRestaurants: Array<any>;
  restaurants: any;
  url: 'http://localhost:8080/ubifeed/';
  venueId: any;

  constructor(private activatedRoute: ActivatedRoute,
              private storageService: LocalstorageService,
              private restaurantService: RestaurantService,
              private http: HttpClient) { }

  ngOnInit() {
    this.venueId = this.activatedRoute.snapshot.paramMap.get('id');
    this.getData();
    console.log(this.restaurants);
  }

  getData() {
    this.restaurantService.getAllRestaurants(this.venueId);
    this.storageService.getRestaurants('restaurants')
      .then((res) => {
        this.restaurants = res;
        console.log(res);
      });
  }

}
