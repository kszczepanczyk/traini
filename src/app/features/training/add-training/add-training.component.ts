import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { NgxMaterialTimepickerTheme } from 'ngx-material-timepicker';
import { Client, UserListResp } from 'src/app/models/user.model';
import { DataService } from 'src/app/shared/data.service';
import { TrainingService } from '../training.service';
import { UsersService } from '../../users/users.service';

@Component({
  selector: 'app-add-training',
  templateUrl: './add-training.component.html',
  styleUrls: ['./add-training.component.scss'],
})
export class AddTrainingComponent implements OnInit {
  trainingForm: FormGroup;
  clients: UserListResp[] = [];
  clientId: number | null = null;
  isSubmitted: boolean = false;
  today: Date = new Date();
  isLoaded: boolean = false;
  error: string = '';
  constructor(
    private formBuilder: FormBuilder,
    private data_service: DataService,
    private _router: Router,
    private _trainingService: TrainingService,
    private _userService: UsersService
  ) {
    this.clientId = this.data_service.getData();
    this.trainingForm = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      date: new FormControl('', Validators.required),
      timeFrom: new FormControl('', Validators.required),
      timeTo: new FormControl('', Validators.required),
      client: new FormControl(this.clientId, Validators.required),
      cyclic: new FormControl(false),
      cyclicDay: new FormControl(''),
      details: new FormControl(''),
      localization: new FormControl(''),
    });

    this.onCheckboxChange();
  }
  ngOnInit(): void {
    this._userService.getUserList().subscribe(
      (res) => {
        this.clients = res.data;
        this.isLoaded = true;
      },
      (_) => {
        this.error = 'Coś poszło nie tak';
        this.isLoaded = true;
      }
    );
  }

  get f(): { [key: string]: AbstractControl } {
    return this.trainingForm.controls;
  }

  theme: NgxMaterialTimepickerTheme = {
    container: {
      buttonColor: '#6c8eff',
    },
    dial: {
      dialBackgroundColor: '#6c8eff',
    },
    clockFace: {
      clockHandColor: '#6c8eff',
    },
  };

  goBack() {}

  onSubmitTraining() {
    this.isSubmitted = true;
    if (this.trainingForm.valid) {
      this._trainingService
        .addTraining(this.trainingForm.value)
        .subscribe((res) => {
          console.log('dodano');
        });
    }
  }

  onCheckboxChange() {
    const isCyclic = this.trainingForm.get('cyclic')!.value;
    if (isCyclic) {
      this.trainingForm.get('cyclicDay')!.setValidators([Validators.required]);
    } else {
      this.trainingForm.get('cyclicDay')!.clearValidators();
    }
    this.trainingForm.get('cyclicDay')!.updateValueAndValidity();
  }

  onClientChange() {
    const selectedClientId = this.trainingForm.get('client')!.value;
    if (selectedClientId === 'inne') {
      this.data_service.setData({ training: this.trainingForm.value });
      this._router.navigate(['/clients/add']);
    }
  }
}
