import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LocalstorageService } from './localstorage.service';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';


@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  url = 'http://localhost:8080/ubifeed/';
  user: Object;

  constructor(private http: HttpClient,
              private localStorage: LocalstorageService,
              private router: Router,
              private toastController: ToastController) { }

  register(body: any, headers: any) {
    console.log(body);
    this.http.post(this.url, body, headers)
      .subscribe((data) => {
        console.log(data)
        this.user = data;
        if(data != null) {
          this.localStorage.setUser('user', data)
          .then(() => {
            console.log('user is saved');
          }).catch(e => {
            console.log(e);
          });
          this.router.navigateByUrl('/menu/venues');
        } else {
          this.showToast('Signup failed, please try again');
        }

      }, error =>
        console.log(error)
      );
  }

  async showToast(text: string) {
    const toast = await this.toastController.create({
      message: text,
      duration: 2000
    });
    toast.present();
  }
}
