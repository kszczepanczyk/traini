import { Component } from '@angular/core';
import { Client } from 'src/app/models/user.model';
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MockApiService } from 'src/app/mock-api.service';
@Component({
  selector: 'app-client-edit',
  templateUrl: './client-edit.component.html',
  styleUrls: ['./client-edit.component.scss'],
})
export class ClientEditComponent {
  isSubmitted: boolean = false;
  userForm: FormGroup;
  clientId: number = 0;
  localizationForm: FormGroup;
  tagForm: FormGroup;
  userData: Client | null = null;
  isTagModalVisible: boolean = false;
  isLocalizationModalVisible: boolean = false;
  constructor(
    private formBuilder: FormBuilder,
    private route: ActivatedRoute,
    private _api_service: MockApiService
  ) {
    this.route.paramMap.subscribe((params) => {
      this.clientId = +params.get('id')!;
    });
    this._api_service.getClients().subscribe((res) => {
      this.userData = res.find((client) => {
        return this.clientId === client.id;
      })!;
    });
    this.userForm = this.formBuilder.group({
      name: new FormControl(this.userData?.name, Validators.required),
      surname: new FormControl(this.userData?.surname, Validators.required),
      description: new FormControl(this.userData?.description),
      phone: new FormControl(this.userData?.phone),
      city: new FormControl(this.userData?.city),
      gender: new FormControl(this.userData?.gender),
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
    if (this.userForm.valid) {
      const { name, surname, description, phone, city, gender } =
        this.userForm.value;
      const { photoB64, email, id } = this.userData!;
      //TODO
    }
    //TODO
    //send to server
  }
  onFileChange(event: any) {
    const files = event.target.files as FileList;

    if (files.length > 0) {
      const _file = URL.createObjectURL(files[0]);
      this.userData!.photoB64 = _file;
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
}
