import { Injectable } from '@angular/core';
import { Storage } from '@ionic/storage';

@Injectable({
  providedIn: 'root'
})

export class LocalstorageService {

  constructor(public storage: Storage) {
    console.log('Local storage working!');
  }

  /*  Set the user's email  */
  async setUser(key: string, value: any): Promise<any> {
    try {
      const result = await this.storage.set(key, value);
      console.log('Set string in storage: ' + result);
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

}
