import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { TabmenuPage } from './tabmenu.page';

const routes: Routes = [
  {
    path: '',
    component: TabmenuPage,
    children: [
      {
        path: 'food',
        loadChildren: () => import('./foodtab/foodtab.module').then(m => m.FoodtabPageModule)
      },
      {
        path: 'drink',
        loadChildren: () => import('./drinktab/drinktab.module').then(m => m.DrinktabPageModule)
      },
      {
        path: 'basket',
        loadChildren: () => import('./shoppingbasket/shoppingbasket.module').then(m => m.ShoppingbasketPageModule)
      }
    ]
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [TabmenuPage]
})
export class TabmenuPageModule {}
