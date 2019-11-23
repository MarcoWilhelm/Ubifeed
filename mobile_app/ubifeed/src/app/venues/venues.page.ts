import { Component, OnInit } from '@angular/core';
import { LocalstorageService } from '../services/localstorage.service';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpParams, HttpHandler, HttpHeaders } from '@angular/common/http';
import { VenueService } from '../services/venue.service';
import { RestaurantService } from '../services/restaurant.service';


@Component({
  selector: 'app-venues',
  templateUrl: './venues.page.html',
  styleUrls: ['./venues.page.scss'],
})
export class VenuesPage implements OnInit {

  venues : any;
  url: string;

  constructor(private storageService: LocalstorageService,
              private activatedRoute: ActivatedRoute,
              private venueService: VenueService,
              private http: HttpClient,
              private restaurantService: RestaurantService,
              private router: Router) { }

  ngOnInit() {
    this.getData();
    // this.setRestaurants();
  }

  getData() {
    // this.venueService.getAllVenues();
    this.storageService.getVenues('venues')
      .then((res) => {
        this.venues = res;
        console.log(res);
      });
  }

  loadRestaurants(venueId: any) {
    this.url = '/menu/restaurants/' + venueId;
    this.restaurantService.getAllRestaurants()
      .then(() => {
        this.router.navigateByUrl(this.url);
      });
    
  }
}
