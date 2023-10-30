import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MockApiService } from 'src/app/mock-api.service';
import { Client } from 'src/app/models/user.model';
import { DataService } from 'src/app/shared/data.service';

@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrls: ['./client-profile.component.scss'],
})
export class ClientProfileComponent implements OnInit {
  clientId: number = 0;
  user: Client | null = null;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private _api_service: MockApiService,
    private data_service: DataService
  ) {
    this.route.paramMap.subscribe((params) => {
      this.clientId = +params.get('id')!;
    });
  }
  ngOnInit(): void {
    this._api_service.getClients().subscribe((res) => {
      this.user = res.find((client) => {
        return this.clientId === client.id;
      })!;
    });
  }
  openAddTraining() {
    this.data_service.setData(this.clientId);
    this.router.navigate(['/training/add']);
  }
  openAddProgress() {
    this.data_service.setData(this.clientId);
    this.router.navigate(['/add-progress']);
  }
}
