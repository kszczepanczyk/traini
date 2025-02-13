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
import { UserListResp } from 'src/app/models/user.model';
import { Localization } from 'src/app/models/location.model';
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
  locations: Localization[];
  clientId: number = null;
  isSubmitted: boolean = false;
  today: Date = new Date();
  isLoaded: boolean = false;
  isAdding: boolean = false;
  error: string = '';
  result: string = '';
  constructor(
    private formBuilder: FormBuilder,
    private data_service: DataService,
    private _router: Router,
    private _trainingService: TrainingService,
    private _userService: UsersService
  ) {
    this.clientId = this.data_service.getData('clientId')
      ? this.data_service.getData('clientId')
      : null;
    const dateFromService = this.data_service.getData('date')
      ? this.data_service.getData('date')
      : null;
    this.trainingForm = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      date: new FormControl(dateFromService, Validators.required),
      timeFrom: new FormControl('', Validators.required),
      timeTo: new FormControl('', Validators.required),
      client: new FormControl(this.clientId, Validators.required),
      cyclic: new FormControl(false),
      details: new FormControl(''),
      localization: new FormControl('', Validators.required),
    });
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
    this._userService.getLocations().subscribe(
      (res) => {
        this.locations = res.data;
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

  goBack() {
    const url = this.data_service.getData('callbackURL')
      ? this.data_service.getData('callbackURL')
      : '';
    this.data_service.deleteData('clientId');
    this.data_service.deleteData('date');
    this._router.navigate([url]);
  }

  onSubmitTraining() {
    this.isSubmitted = true;

    if (this.trainingForm.valid) {
      const {
        name,
        date,
        timeFrom,
        timeTo,
        client,
        details,
        localization,
        cyclic,
      } = this.trainingForm.value;
      let dateObject = new Date(date);

      let year = dateObject.getFullYear();
      let month = (dateObject.getMonth() + 1).toString().padStart(2, '0');
      let day = dateObject.getDate().toString().padStart(2, '0');

      let combinedStart = `${year}-${month}-${day}T${timeFrom}:00.000+00:00`;
      let combinedEnd = `${year}-${month}-${day}T${timeTo}:00.000+00:00`;

      const dataToSend = {
        name: name,
        userId: client,
        trainingDate: {
          start: combinedStart,
          end: combinedEnd,
        },
        cycled: cyclic,
        description: details,
        localization: localization,
      };
      this.isAdding = true;
      this._trainingService.addTraining(dataToSend).subscribe(
        (res) => {
          this.result = 'SUCCESS';
          this.isAdding = false;
        },
        (_) => {
          this.result = 'ERROR';
          this.isAdding = false;
        }
      );
    }
  }

  onClientChange() {
    // const selectedClientId = this.trainingForm.get('client')!.value;
    // if (selectedClientId === 'inne') {
    //   this.data_service.setData({ training: this.trainingForm.value });
    //   this._router.navigate(['/clients/add']);
    // }
  }
}
