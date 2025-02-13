import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Client } from 'src/app/models/user.model';
import { DataService } from 'src/app/shared/data.service';
import { UsersService } from '../../users.service';

@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrls: ['./client-profile.component.scss'],
})
export class ClientProfileComponent implements OnInit {
  clientId: number = null;
  isLoaded: boolean = false;
  user: Client;
  error: string = '';
  constructor(
    private _activatedRoute: ActivatedRoute,
    private router: Router,
    private data_service: DataService,
    private _userService: UsersService,
    private _dataService: DataService
  ) {
    this._activatedRoute.paramMap.subscribe((params) => {
      this.clientId = +params.get('id')!;
    });
  }
  ngOnInit(): void {
    this._userService.getClient(this.clientId).subscribe(
      (res) => {
        this.user = res.data;
        this.isLoaded = true;
      },
      (_) => {
        this.error = 'Coś poszło nie tak';
        this.isLoaded = true;
      }
    );
  }
  goToTraining(id: number) {
    this._dataService.setData('callbackURL', `/clients/${this.clientId}`);
    this.router.navigate(['/training', id]);
  }
  openAddTraining() {
    this.data_service.setData('clientId', this.clientId);
    this.data_service.setData('callbackURL', `/clients/${this.clientId}`);
    this.router.navigate(['/training/add']);
  }
  openAddProgress() {
    this.router.navigate(['./progress/add'], {
      relativeTo: this._activatedRoute,
    });
  }
  goToEdit() {
    this._dataService.setData('clientData', this.user);
    this.router.navigate(['/clients', this.clientId, 'edit']);
  }
  goToProgress(id: number) {
    this.router.navigate(['/clients', this.clientId, 'progress', id]);
  }
  deleteClient() {
    this.isLoaded = false;
    this._userService.deleteUser(this.clientId).subscribe(
      (res) => {
        this.isLoaded = true;
        this.router.navigate(['/clients']);
      },
      (err) => {
        this.isLoaded = true;
        this.error = 'ERROR';
      }
    );
  }
}
