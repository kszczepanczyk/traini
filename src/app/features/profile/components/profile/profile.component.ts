import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/features/auth/auth.service';
import { Trainer } from 'src/app/models/user.model';
import { DataService } from 'src/app/shared/data.service';
import { ProfileService } from '../../profile.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent {
  user: Trainer;
  isLoaded: boolean = false;
  error: string = '';
  constructor(
    private _profileService: ProfileService,
    private _dataService: DataService,
    private _router: Router,
    private _authService: AuthService
  ) {
    this._profileService.getProfile().subscribe(
      (res) => {
        this.isLoaded = true;
        this.user = res.data;
      },
      (_) => {
        this.isLoaded = true;
        this.error = 'Coś poszło nie tak';
      }
    );
  }

  goToEdit(): void {
    this._dataService.setData('userData', this.user);
    this._router.navigate(['profile/edit']);
  }

  logout() {
    this._authService.logout();
  }
}
