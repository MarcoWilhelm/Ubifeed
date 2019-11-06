import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { VenuesPage } from './venues.page';
import { RestaurantsPage } from '../restaurants/restaurants.page';

const routes: Routes = [
  {
    path: '',
    component: VenuesPage,
    children: [
      {
        path: 'restaurants',
        loadChildren: () => import('../restaurants/restaurants.module').then(m => m.RestaurantsPageModule)
      }
    ]
  },
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [VenuesPage]
})
export class VenuesPageModule {}
