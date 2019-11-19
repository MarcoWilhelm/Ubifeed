import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http';
import { LocalstorageService } from './localstorage.service';
import { Router } from '@angular/router';
import { ToastController } from '@ionic/angular';


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  url = 'http://localhost:8080/ubifeed/';
  user: Object;

  constructor(private http: HttpClient,
              private localStorage: LocalstorageService,
              private router: Router,
              private toastController: ToastController) { }

  login(body: any, headers: any) {
    this.http.post(this.url, body, headers)
      .subscribe((data) => {
        console.log(data);
        this.user = data;
        if (this.user != null) {
          this.localStorage.setUser('user', data)
          .then(() => {
            console.log('user is saved');
          }).catch(e => {
            console.log(e);
          });
        this.router.navigateByUrl('/menu/venues');
        } else {
          this.showToast('Credentials wrong!');
        }
      });
  }

  async showToast(text: string) {
    const toast = await this.toastController.create({
      message: text,
      duration: 2000
    });
    toast.present();
  }

}
