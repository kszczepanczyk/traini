import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MockApiService } from 'src/app/mock-api.service';
import { Trainer } from 'src/app/models/user.model';
import { DataService } from 'src/app/shared/data.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  constructor(
    private _api_service: MockApiService,
    private _dataService: DataService,
    private _router: Router
  ) {}
  user: Trainer | null = null;

  ngOnInit() {
    this._api_service.getUserInfo().subscribe((res) => {
      this.user = res;
    });
  }

  goToEdit(): void {
    this._dataService.setData(this.user);
    this._router.navigate(['profile/edit']);
  }
}
