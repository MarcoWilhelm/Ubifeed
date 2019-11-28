import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TabserviceService } from '../../services/tabservice.service';
import { LocalstorageService } from '../../services/localstorage.service';

@Component({
  selector: 'app-drinktab',
  templateUrl: './drinktab.page.html',
  styleUrls: ['./drinktab.page.scss'],
})
export class DrinktabPage implements OnInit {

  meals: any;

  constructor(private tabService: TabserviceService,
              private localStorage: LocalstorageService) { }

  ngOnInit() {
    this.getData();
  }

  getData() {
    this.localStorage.getMeals('meals')
      .then((res) => {
        this.meals = res;
      });
  }

}
