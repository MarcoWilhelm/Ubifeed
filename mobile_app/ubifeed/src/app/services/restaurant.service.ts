import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpResponse } from '@angular/common/http';
import { LocalstorageService } from '../services/localstorage.service';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  url = 'http://localhost:8080/ubifeed/?action=get-all-restaurants&venueId=';
  restaurants: any;

  constructor(private http: HttpClient,
              private localStorage: LocalstorageService) { }

  async getAllRestaurants(venueId: any) {
    let urlWithParams = this.url + venueId;
    console.log(urlWithParams);
    this.http.get(urlWithParams)
      .subscribe((data) => {
        console.log("Result GET", data);
        this.localStorage.setRestaurants("restaurants", data);
      });
  }

}
