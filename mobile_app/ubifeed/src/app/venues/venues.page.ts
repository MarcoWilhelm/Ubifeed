import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-venues',
  templateUrl: './venues.page.html',
  styleUrls: ['./venues.page.scss'],
})
export class VenuesPage implements OnInit {

  venues : any;

  constructor() { }

  ngOnInit() {
    fetch('../../assets/data/venues.json').then(res => res.json())
    .then(json => {
      this.venues = json;
    });
  }

}
