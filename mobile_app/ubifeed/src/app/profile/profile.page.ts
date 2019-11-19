import { Component, OnInit } from '@angular/core';
import { LocalstorageService } from '../services/localstorage.service';


@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  user: any;
  name: string;
  phone: string;
  email: string;
  

  constructor(private storageService: LocalstorageService) { }

  ngOnInit() {
    this.storageService.getUser('user')
      .then((res) => {
        this.user = res;
        console.log(this.user);
        this.name = this.user.firstName + " " + this.user.lastName;
        this.phone = this.user.phone;
        this.email = this.user.email;
      }).catch(e => {
        console.log(e);
      });
  }

  logout() {
    this.storageService.removeUser('user');
  }

}
