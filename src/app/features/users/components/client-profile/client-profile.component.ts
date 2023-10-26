import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-client-profile',
  templateUrl: './client-profile.component.html',
  styleUrls: ['./client-profile.component.scss'],
})
export class ClientProfileComponent implements OnInit {
  clientId: number = 0;
  constructor(private route: ActivatedRoute) {
    this.route.paramMap.subscribe((params) => {
      this.clientId = +params.get('id')!;
    });
  }
  ngOnInit(): void {}
}
