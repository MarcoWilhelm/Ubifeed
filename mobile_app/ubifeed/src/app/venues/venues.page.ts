import { Component, OnInit } from '@angular/core';
import { LocalstorageService } from '../services/localstorage.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-venues',
  templateUrl: './venues.page.html',
  styleUrls: ['./venues.page.scss'],
})
export class VenuesPage implements OnInit {

  venues : any;

  constructor(private storageService: LocalstorageService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    fetch('../../assets/data/venues.json').then(res => res.json())
    .then(json => {
      this.venues = json;
    });
  }

  

  

}
