import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpResponse } from '@angular/common/http';
import { LocalstorageService } from '../services/localstorage.service';

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  url = 'http://localhost:8080/ubifeed/?action=get-all-restaurants';
  restaurants: any;

  constructor(private http: HttpClient,
              private localStorage: LocalstorageService) { }

  getAllRestaurants() {
    this.http.get(this.url)
      .subscribe((data) => {
        console.log("Result GET", data);
        this.localStorage.setRestaurants("restaurants", data);
      });
  }

}
