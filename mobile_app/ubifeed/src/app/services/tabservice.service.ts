import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHandler, HttpHeaders } from '@angular/common/http';
import { LocalstorageService } from './localstorage.service';

@Injectable({
  providedIn: 'root'
})
export class TabserviceService {

  url = 'http://localhost:8080/ubifeed/?action=get-all-meals&restaurantId=';

  constructor(private http: HttpClient,
              private localStorage: LocalstorageService) { }

  getFoodItems(restaurantId: any) {
    let urlWithParams = this.url + restaurantId;
    this.http.get(urlWithParams)
      .subscribe((data) => {
        console.log("RESULT GET", data);
        this.localStorage.setMeals('meals', data);
      });
  }
}
