import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/features/auth/auth.service';
import { MockApiService } from 'src/app/mock-api.service';
import { Trainer } from 'src/app/models/user.model';
import { DataService } from 'src/app/shared/data.service';
import { ProfileService } from '../../profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  user: Trainer;
  dataLoaded: boolean = false;
  constructor(
    private _profileService: ProfileService,
    private _dataService: DataService,
    private _router: Router,
    private _authService: AuthService
  ) {
    this._profileService.getProfile().subscribe(
      (res) => {
        this.dataLoaded = true;
        this.user = res.data;
        console.log(this.user.tags.length);
      },
      (_) => {
        this.dataLoaded = false;
      }
    );
  }

  ngOnInit() {}

  goToEdit(): void {
    this._dataService.setData(this.user);
    this._router.navigate(['profile/edit']);
  }

  logout() {
    this._authService.logout();
  }
}
