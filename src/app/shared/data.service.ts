import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private data: any = {};

  setData(key, value): void {
    this.data[key] = value;
  }
  getData(key): any {
    return this.data[key];
  }
  deleteData(key) {
    delete this.data[key];
  }
}
