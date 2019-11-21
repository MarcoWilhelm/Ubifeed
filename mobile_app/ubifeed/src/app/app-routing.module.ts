import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'login-load', pathMatch: 'full' },
  { path: 'login', loadChildren: './login/login.module#LoginPageModule' },
  { path: 'signup', loadChildren: './signup/signup.module#SignupPageModule' },
  { path: 'menu', loadChildren: () => import('./menu/menu.module').then(m => m.MenuPageModule) },
  { path: 'login-load', loadChildren: './login-load/login-load.module#LoginLoadPageModule' },
  { path: 'tabmenu', loadChildren: './tabmenu/tabmenu.module#TabmenuPageModule' },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
