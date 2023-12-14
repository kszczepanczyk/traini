import { Component, OnInit } from '@angular/core';
import { UserListResp } from 'src/app/models/user.model';
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { UsersService } from '../../users.service';
import { DataService } from 'src/app/shared/data.service';
@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.scss'],
})
export class ClientEditComponent implements OnInit {
  isSubmitted: boolean = false;
  userForm: FormGroup;
  clientId: number = 0;
  localizationForm: FormGroup;
  tagForm: FormGroup;
  userData: UserListResp;
  isTagModalVisible: boolean = false;
  isLocalizationModalVisible: boolean = false;
  error: string = '';
  isLoading: boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private _router: Router,
    private _userService: UsersService,
    private _dataService: DataService
  ) {}
  ngOnInit(): void {
    this.route.paramMap.subscribe((params) => {
      this.clientId = +params.get('id')!;
    });
    if (!this.clientId) {
      this.userData = {
        id: null,
        name: '',
        surname: '',
        description: '',
        phone: '',
        city: '',
        gender: '',
        photo: '',
      };
    } else {
      this.userData = this._dataService.getData('clientData');
    }
    this.userForm = this.formBuilder.group({
      name: new FormControl(this.userData?.name, Validators.required),
      surname: new FormControl(this.userData?.surname, Validators.required),
      description: new FormControl(this.userData?.description),
      phone: new FormControl(this.userData?.phone),
      city: new FormControl(this.userData?.city),
      gender: new FormControl(this.userData?.gender),
      photo: new FormControl(''),
    });
    this.localizationForm = this.formBuilder.group({
      localizationName: new FormControl('', Validators.required),
    });
    this.tagForm = this.formBuilder.group({
      tagName: new FormControl('', Validators.required),
    });
  }
  get f(): { [key: string]: AbstractControl } {
    return this.userForm.controls;
  }
  onSubmitUser() {
    this.isSubmitted = true;
    this.isLoading = true;
    if (this.userForm.valid) {
      if (this.clientId) {
        this._userService
          .updateClient(this.userForm.value, this.clientId)
          .subscribe(
            (res) => {
              this.isLoading = false;
              this._router.navigate(['/clients', this.clientId]);
            },
            (err) => {
              this.isLoading = false;
              this.error = 'Coś poszło nie tak';
            }
          );
      } else {
        this._userService.addClient(this.userForm.value).subscribe(
          (res) => {
            this.isLoading = false;
            this._router.navigate(['clients'], {
              queryParams: { added: 'true' },
            });
          },
          (err) => {
            this.isLoading = false;
            this.error = 'Coś poszło nie tak';
          }
        );
      }
    }
  }
  onFileChange(event: any) {
    debugger;
    const files = event.target.files as FileList;
    if (files.length > 0) {
      const _file = files[0];
      this.convertFileToBase64(_file);
      this.resetInput();
    }
    //TODO
    //send to server
  }
  resetInput() {
    const input = document.getElementById(
      'avatar-input-file'
    ) as HTMLInputElement;
    if (input) {
      input.value = '';
    }
  }

  convertFileToBase64(file: File) {
    const reader = new FileReader();
    reader.onload = () => {
      const base64Data = reader.result?.toString().split(',')[1];
      // Now 'base64Data' contains the file data in base64 format
      this.userData.photo = base64Data;
    };
    reader.readAsDataURL(file);
  }
}
