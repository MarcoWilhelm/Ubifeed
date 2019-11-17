import { Component, OnInit } from '@angular/core';
import { LocalstorageService } from '../services/localstorage.service';

@Component({
  selector: 'app-venues',
  templateUrl: './venues.page.html',
  styleUrls: ['./venues.page.scss'],
})
export class VenuesPage implements OnInit {

  venues : any;

  constructor(private storageService: LocalstorageService) { }

  ngOnInit() {
    fetch('../../assets/data/venues.json').then(res => res.json())
    .then(json => {
      this.venues = json;
    });

    /*for testing*/
    // this.storageService.setUser('user-name', 'testuser');
  }

  

}
