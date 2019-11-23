import { Component, OnInit } from '@angular/core';
import { ToastController } from '@ionic/angular';
import { HttpClient, HttpParams, HttpHandler, HttpHeaders } from '@angular/common/http';
import { LoginService } from '../services/login.service';
import { VenueService } from '../services/venue.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  email: string;
  password: string;

  constructor(public toastController: ToastController,
              private http: HttpClient,
              private loginService: LoginService,
              private venueService: VenueService) { }

  ngOnInit() {
  }

  login() {
    const params = new HttpParams()
      .set('action', 'login-user')
      .set('email', this.email)
      .set('password', this.password);

      const headers = new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded');

    this.loginService.login(params, headers);
    
  }

  async showToast(text: string) {
    const toast = await this.toastController.create({
      message: text,
      duration: 2000
    });
    toast.present();
  }



}
