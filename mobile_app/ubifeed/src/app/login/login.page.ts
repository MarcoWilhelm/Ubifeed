import { Component, OnInit } from '@angular/core';
import { ToastController } from '@ionic/angular';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  email: string;
  password: string;

  loginStatus: boolean;

  constructor(public toastController: ToastController,
              private http: HttpClient) { }

  ngOnInit() {
    this.loginStatus = false;
  }

  login() {
    if (this.email == null) {
      this.showToast('Please enter your email');
    } else if (this.password == null) {
      this.showToast('Please enter your password');
    }
  }

  async showToast(text: string) {
    const toast = await this.toastController.create({
      message: text,
      duration: 2000
    });
    toast.present();
  }

}
