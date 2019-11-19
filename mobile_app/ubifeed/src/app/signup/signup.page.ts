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
    // console.log(form.value);
    
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

  // register() {
  //   console.log('register function begin');
  //   // const formData = new FormData();
  //   // formData.set('action', 'register-user');
  //   // formData.set('firstName', this.firstname);
  //   // formData.set('lastName', this.lastname);
  //   // formData.set('email', this.email);
  //   // formData.set('phone', this.phone);
  //   // formData.set('password', this.password);

  //   const formData = {
  //     'action': 'register-user',
  //     'firstName': 'Test',
  //     'lastName': 'Test',
  //     'email': '1234',
  //     'phone': '1234',
  //     'password': 'test'
  //   }


  //   console.log(this.firstname);
  //   console.log(formData);
  //   const httpOptions = {
  //     headers: new HttpHeaders({
  //       'Content-Type': 'application/json',
  //       'Accept': '*/*'
  //     })
  //   };

  //   this.registerService.register(formData, httpOptions);

  // }

  async showToast(text: string) {
    const toast = await this.toastController.create({
      message: text,
      duration: 2000
    });
    toast.present();
  }

}
