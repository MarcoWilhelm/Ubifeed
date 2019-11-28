import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { TabserviceService } from '../../services/tabservice.service';
import { LocalstorageService } from '../../services/localstorage.service';

@Component({
  selector: 'app-foodtab',
  templateUrl: './foodtab.page.html',
  styleUrls: ['./foodtab.page.scss'],
})
export class FoodtabPage implements OnInit {

  restaurantId: any;
  meals: any;

  constructor(private activatedRoute: ActivatedRoute,
              private tabService: TabserviceService,
              private localStorage: LocalstorageService) { }

  ngOnInit() {
    this.restaurantId = this.activatedRoute.snapshot.paramMap.get('id');
    console.log(this.restaurantId);
    this.getData();
  }

  getData() {
    this.tabService.getFoodItems(this.restaurantId);
    this.localStorage.getMeals('meals')
      .then((res) => {
        this.meals = res;
        console.log(res);
      });
  }
}
