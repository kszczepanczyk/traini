import { Component, OnInit } from '@angular/core';
import { Trainer } from 'src/app/models/user.model';
import { DataService } from 'src/app/shared/data.service';
import {
  FormBuilder,
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss'],
})
export class EditComponent {
  isSubmitted: boolean = false;
  userForm: FormGroup;
  localizationForm: FormGroup;
  tagForm: FormGroup;
  userData: Trainer | null = null;
  isTagModalVisible: boolean = false;
  isLocalizationModalVisible: boolean = false;
  constructor(
    private _dataService: DataService,
    private formBuilder: FormBuilder,
    private _router: Router
  ) {
    this.userData = this._dataService.getData();
    this.userForm = this.formBuilder.group({
      name: new FormControl(this.userData?.name, Validators.required),
      surname: new FormControl(this.userData?.surname, Validators.required),
      description: new FormControl(this.userData?.description),
      phone: new FormControl(this.userData?.phone),
      city: new FormControl(this.userData?.city),
      gender: new FormControl(this.userData?.gender),
      tags: new FormControl(this.userData?.tags),
      localizations: new FormControl(this.userData?.localizations),
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
  deleteTag(tagId: number) {
    this.userData!.tags = this.userData!.tags.filter((tag) => tag.id != tagId);
  }
  deleteLocalization(localizationId: number) {
    this.userData!.localizations = this.userData!.localizations.filter(
      (localization) => localization.id != localizationId
    );
  }
  openTagModal() {
    this.isTagModalVisible = true;
  }
  openLocalizationModal() {
    this.isLocalizationModalVisible = true;
  }
  onSubmitTag() {
    if (this.tagForm.valid) {
      const { tagName } = this.tagForm.value;
      this.userData?.tags.push({
        id: Math.floor(Math.random() * 100),
        name: tagName,
      });
      //TODO
      //send to server
      this.tagForm.reset();
      this.isTagModalVisible = false;
    }
  }
  onSubmitLocalization() {
    if (this.localizationForm.valid) {
      const { localizationName } = this.localizationForm.value;
      this.userData?.localizations.push({
        id: Math.floor(Math.random() * 100),
        name: localizationName,
      });
      //TODO
      //send to server
      this.localizationForm.reset();
      this.isLocalizationModalVisible = false;
    }
  }
  onSubmitUser() {
    this.isSubmitted = true;
    if (this.userForm.valid) {
      const { name, surname, description, phone, city, gender } =
        this.userForm.value;
      const { tags, localizations, photoB64, email, id } = this.userData!;
      this.userData = {
        id,
        name,
        surname,
        email,
        description,
        phone,
        city,
        gender,
        tags,
        localizations,
        photoB64,
      };
    }
    //TODO
    //send to server
    this._router.navigate(['/profile']);
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
