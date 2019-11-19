import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HTTP } from '@ionic-native/http/ngx';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  url = 'http://localhost:8080/ubifeed/';

  constructor(private http: HttpClient) { }

  register(body: any, headers: any) {
    console.log(body);
    this.http.post(this.url, body, headers)
      .subscribe((data) => {
        console.log(data)
      }, error =>
        console.log(error)
      );
  };

  // register(form, httpOptions) {
    
  //   console.log(form.value);
  //   this.http.post(this.url, form.value)
  //     .subscribe((res) => {
  //       console.log(res);
  //     }, error => console.log(error)
  //   )}
}
