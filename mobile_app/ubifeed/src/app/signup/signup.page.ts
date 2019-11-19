import { Component, OnInit } from '@angular/core';
import { ToastController } from '@ionic/angular';
import { RegisterService } from '../services/register.service';
import { HttpHeaders, HttpParams } from '@angular/common/http';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.page.html',
  styleUrls: ['./signup.page.scss'],
})
export class SignupPage implements OnInit {

  action: string;
  firstname: string;
  lastname: string;
  phone: string;
  email: string;
  password: string;
  passwordRepeat: string;

  url: string;
  user: any;

  submitStatus: boolean;

  constructor(public toastController: ToastController,
              private registerService: RegisterService) { }

  ngOnInit() {
    this.submitStatus = false;
    this.url = 'http://localhost:8080/ubifeed/';
  }

  register() {    
      const params = new HttpParams()
        .set('action', 'register-user')
        .set('firstName', 'Hello')
        .set('lastName', this.lastname)
        .set('email', this.email)
        .set('phone', this.phone)
        .set('password', this.password);

      const headers = new HttpHeaders()
        .set('Content-Type', 'application/x-www-form-urlencoded');

    this.registerService.register(params, headers);
  }


  async showToast(text: string) {
    const toast = await this.toastController.create({
      message: text,
      duration: 2000
    });
    toast.present();
  }

}
