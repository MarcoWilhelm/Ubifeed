import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { LocalstorageService } from '../services/localstorage.service';

@Component({
  selector: 'app-login-load',
  templateUrl: './login-load.page.html',
  styleUrls: ['./login-load.page.scss'],
})
export class LoginLoadPage implements OnInit {

  constructor(public storageService: LocalstorageService,
              private router: Router) { }

  ngOnInit() {
    this.requestUser();
  }

  requestUser() {
    this.storageService.getUser('user-name').then(result => {
      if (result != null) {
        this.router.navigateByUrl('/menu/venues');
      } else {
        this.router.navigateByUrl('/login');
      }
    }).catch(e => {
      console.log('Error: ' + e);
    })
  }

}
