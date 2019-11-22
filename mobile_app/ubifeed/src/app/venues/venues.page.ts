import { Component, OnInit } from '@angular/core';
import { LocalstorageService } from '../services/localstorage.service';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpParams, HttpHandler, HttpHeaders } from '@angular/common/http';
import { VenueService } from '../services/venue.service';


@Component({
  selector: 'app-venues',
  templateUrl: './venues.page.html',
  styleUrls: ['./venues.page.scss'],
})
export class VenuesPage implements OnInit {

  venues : any;
  url: 'http://localhost:8080/ubifeed/';

  constructor(private storageService: LocalstorageService,
              private activatedRoute: ActivatedRoute,
              private venueService: VenueService,
              private http: HttpClient) { }

  ngOnInit() {
    this.venueService.getAllVenues();
    this.storageService.getVenues('venues')
      .then((res) => {
        this.venues = res;
        console.log(res);
      });    
  }

}
