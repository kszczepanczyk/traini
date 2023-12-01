import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserListResp } from 'src/app/models/user.model';
import { UsersService } from '../../users.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss'],
})
export class ListComponent implements OnInit {
  clients: UserListResp[] = [];
  listLoaded: boolean = false;
  error: string = '';
  constructor(private _router: Router, private _userService: UsersService) {}

  ngOnInit() {
    this._userService.getUserList().subscribe(
      (res) => {
        this.clients = res.data;
        this.listLoaded = true;
      },
      (_) => {
        this.error = 'Coś poszło nie tak';
        this.listLoaded = true;
      }
    );
  }

  navigateToAddClient() {
    this._router.navigate(['/clients/add']);
  }
}
