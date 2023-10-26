import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MockApiService } from 'src/app/mock-api.service';
import { Client } from 'src/app/models/user.model';
import { DataService } from 'src/app/shared/data.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
})
export class ListComponent implements OnInit {
  clients: Client[] = [];
  constructor(
    private _apiService: MockApiService,
    private _dataService: DataService,
    private _router: Router
  ) {}

  ngOnInit() {
    this._apiService.getClients().subscribe((res) => {
      this.clients = res;
    });
  }
  goToClient(client: Client) {
    this._dataService.setData(client);
    this._router.navigate(['profile/edit']);
  }
}
