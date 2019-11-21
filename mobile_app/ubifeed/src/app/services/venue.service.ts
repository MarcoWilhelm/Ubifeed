import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocalstorageService } from '../services/localstorage.service';

@Injectable({
  providedIn: 'root'
})
export class VenueService {

  url = 'http://localhost:8080/ubifeed/?action=get-all-venues';
  venues: any;

  constructor(private http: HttpClient,
              private localStorage: LocalstorageService) { }

  getAllVenues() {
    this.http.get(this.url)
      .subscribe((data) => {
        console.log(data);
        this.localStorage.setVenues("venues", data);
      });
  }
}
