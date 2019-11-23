import { Component, OnInit } from '@angular/core';
import { LocalstorageService } from '../services/localstorage.service';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  user: any;

  action: string;
  firstname: string;
  lastname: string;
  phone: string;
  email: string;
  password: string;
  passwordRepeat: string;
  

  constructor(private storageService: LocalstorageService) { }

  ngOnInit() {
    this.storageService.getUser('user')
      .then((res) => {
        this.user = res;
        this.firstname = res.firstName;
        this.lastname = res.lastName;
        this.phone = res.phone;
        this.email = res.email;
        console.log(this.firstname);
        console.log(this.user);
      }).catch(e => {
        console.log(e);
      });

  }

  logout() {
    this.storageService.removeUser('user');
  }

}
