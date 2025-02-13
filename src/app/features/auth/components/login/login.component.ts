import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });
  isSubmitted: boolean = false;
  isLoading: boolean = false;
  error: string = '';
  infoMessage: string = '';
  constructor(
    private _formBuilder: FormBuilder,
    private _router: Router,
    private _authService: AuthService,
    private _route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this._route.queryParams.subscribe((params) => {
      if (
        params['registered'] !== undefined &&
        params['registered'] === 'true'
      ) {
        this.infoMessage = 'Rejestracja przebiegła pomyślnie';
      }
    });
    this.loginForm = this._formBuilder.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }
  get f(): { [key: string]: AbstractControl } {
    return this.loginForm.controls;
  }
  login() {
    this.isSubmitted = true;
    this.isLoading = true;
    if (this.loginForm.valid) {
      this._authService.login(this.loginForm.value).subscribe(
        (_) => {
          this.isLoading = false;
          this._router.navigate(['/']);
        },
        (res) => {
          this.isLoading = false;
          this.error = JSON.stringify(res);
        }
      );
    }
  }
}
