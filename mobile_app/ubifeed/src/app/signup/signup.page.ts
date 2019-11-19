import { Component, OnInit } from '@angular/core';
import { ToastController } from '@ionic/angular';
import { HttpClient } from '@angular/common/http';

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

  user: any;

  submitStatus: boolean;

  constructor(public toastController: ToastController,
              private http: HttpClient) { }

  ngOnInit() {
    this.submitStatus = false;
    
  }

  signup() {
    if (this.firstname == null) {
      this.showToast('Please enter your firstname');
    } else if (this.lastname == null) {
      this.showToast('Please enter your lastname');
    } else if (this.phone == null) {
      this.showToast('Please enter your phone number');
    } else if (this.email == null) {
      this.showToast('Please enter your email');
    } else if (this.password == null) {
      this.showToast('Please enter a password');
    } else if (this.password !== this.passwordRepeat) {
      this.showToast('Your passwords do not match');
    } else {
      this.submitStatus = true;
    }

    if (this.submitStatus) {
      this.http.post('http://localhost:8080/ubifeed', {
        action: 'register-user',
        firstName: this.firstname,
        lastName: this.lastname,
        phone: this.phone,
        email: this.email,
        password: this.password
      }).subscribe((response) => {
        this.user = response;
      });
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
