import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})

export class LocalstorageService {

  constructor(public storage: Storage,
              private router: Router) {
    console.log('Local storage working!');
  }

  /*  Set the user object  */
  async setUser(key: string, user: Object): Promise<any> {
    try {
      const result = await this.storage.set(key, user);
      console.log('Set object in storage: ' + result);
      return true;
    } catch (e) {
      console.log(e);
      return false;
    }
  }

  /*  Get the user's email  */
  async getUser(key: string): Promise<any> {
    try {
      const result = await this.storage.get(key);
      console.log('storage getUser result: ' + result);
      if (result != null) {
        return result;
      }
      return null;
    } catch (e) {
      console.log(e);
      return null;
    }
  }

  removeUser(key: string) {
    this.storage.remove(key);
    this.router.navigateByUrl('/login');
  }

  async setVenues(key: string, venues: any) {
    try {
      const result = await this.storage.set(key, venues);
      return true;
    } catch(e) {
      return false;
    }
  }

  async getVenues(key: string): Promise<any> {
    try {
      const result = await this.storage.get(key);
      if (result != null) {
        return result;
      }
      return null;
    } catch (e) {
      return null;
    }
  }

  removeVenues() {
    try {
      this.storage.remove('venues');
    } catch(ex) {
      console.log(ex);
    }
  }

  async setRestaurants(key: string, restaurants: any) {
    try {
      const result = await this.storage.set(key, restaurants);
      return true;
    } catch(e) {
      return false;
    }
  }

  async getRestaurants(key: string): Promise<any> {
    try {
      const result = this.storage.get(key);
      if (result != null) {
        return result;
      }
      return null;
    } catch (e) {
      return null;
    }
  }

  removeRestaurants() {
    try {
      this.storage.remove('restaurants');
    } catch(ex) {
      console.log(ex);
    }
  }

  async setMeals(key: string, meals: any) {
    try {
      const result = await this.storage.set(key, meals);
      return true;
    } catch(e) {
      return false;
    }
  }

  async getMeals(key: string): Promise<any> {
    try {
      const result = this.storage.get(key);
      if (result != null) {
        return result;
      }
      return null;
    } catch (e) {
      return null;
    }
  }

}
