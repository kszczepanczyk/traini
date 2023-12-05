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
import { ProfileService } from '../../profile.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss'],
})
export class EditComponent implements OnInit {
  isSubmitted: boolean = false;
  userForm: FormGroup;
  localizationForm: FormGroup;
  tagForm: FormGroup;
  userData: Trainer = null;
  isTagModalVisible: boolean = false;
  isLocalizationModalVisible: boolean = false;
  isLoaded: boolean = false;
  error: string = '';
  constructor(
    private _dataService: DataService,
    private formBuilder: FormBuilder,
    private _router: Router,
    private _profileService: ProfileService
  ) {}
  ngOnInit(): void {
    this.userData = this._dataService.getData();
    if (this.userData) {
      this.initializeForms(this.userData);
      this.isLoaded = true;
    } else if (!this.userData) {
      this._profileService.getProfile().subscribe(
        (res) => {
          this.userData = res.data;
          this.initializeForms(this.userData);
          this.isLoaded = true;
        },
        (err) => {
          this.error = 'Coś poszło nie tak';
          this.isLoaded = true;
        }
      );
    }
  }

  initializeForms(userData) {
    this.userForm = this.formBuilder.group({
      name: new FormControl(userData.name, Validators.required),
      surname: new FormControl(userData.surname, Validators.required),
      description: new FormControl(userData.description),
      phone: new FormControl(userData.phone),
      city: new FormControl(userData.city),
      gender: new FormControl(userData.gender),
      tags: new FormControl(userData.tags),
      localizations: new FormControl(userData.locations),
    });
    this.localizationForm = this.formBuilder.group({
      localizationName: new FormControl('', [
        Validators.required,
        Validators.maxLength(25),
      ]),
    });
    this.tagForm = this.formBuilder.group({
      tagName: new FormControl('', [
        Validators.required,
        Validators.maxLength(15),
      ]),
    });
  }
  get f(): { [key: string]: AbstractControl } {
    return this.userForm.controls;
  }
  deleteTag(tagToDelete: string) {
    this.userData!.tags = this.userData!.tags.filter(
      (tag) => tag != tagToDelete
    );
  }
  deleteLocalization(localizationToDelete: string) {
    this.userData!.locations = this.userData!.locations.filter(
      (localization) => localization != localizationToDelete
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
      this.userData.tags.push(tagName);
      this.tagForm.reset();
      this.isTagModalVisible = false;
    }
    return false;
  }
  onSubmitLocalization() {
    if (this.localizationForm.valid) {
      const { localizationName } = this.localizationForm.value;
      this.userData.locations.push(localizationName);

      this.localizationForm.reset();
      this.isLocalizationModalVisible = false;
    }
    return false;
  }
  closeTagModal() {
    this.tagForm.reset();
    this.isTagModalVisible = false;
  }
  closeLocalizationModal() {
    this.localizationForm.reset();
    this.isLocalizationModalVisible = false;
  }
  onSubmitUser() {
    if (this.userForm.valid) {
      this.isLoaded = false;
      const { name, surname, description, phone, city, gender } =
        this.userForm.value;
      const { tags, locations, photoB64, email, id } = this.userData!;
      let payload = {
        id,
        name,
        surname,
        email,
        description,
        phone,
        city,
        gender,
        tags,
        locations,
        photoB64: '',
      };
      this._profileService.modifyProfile(payload).subscribe(
        (res) => {
          this._router.navigate(['profile'], {
            queryParams: { success: 'true' },
          });
        },
        (_) => {
          this.error = 'Nie udało się edytować profilu';
          this.isLoaded = true;
        }
      );
    }

    //TODO
    //send to server
    //this._router.navigate(['/profile']);
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
