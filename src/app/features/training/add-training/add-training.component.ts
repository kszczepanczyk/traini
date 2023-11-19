import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { Router } from '@angular/router';
import { NgxMaterialTimepickerTheme } from 'ngx-material-timepicker';
import { MockApiService } from 'src/app/mock-api.service';
import { Client } from 'src/app/models/user.model';
import { DataService } from 'src/app/shared/data.service';

@Component({
  selector: 'app-add-training',
  templateUrl: './add-training.component.html',
  styleUrls: ['./add-training.component.scss'],
})
export class AddTrainingComponent {
  trainingForm: FormGroup;
  clients: Client[] = [];
  clientId: number | null = null;
  isSubmitted: boolean = false;
  today: Date = new Date();

  constructor(
    private formBuilder: FormBuilder,
    private data_service: DataService,
    private api_service: MockApiService,
    private _router: Router
  ) {
    this.api_service.getClients().subscribe((res) => {
      this.clients = res;
    });
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
    console.log(this.trainingForm);
    if (this.trainingForm.valid) {
      // TODO: send to server
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
    console.log(selectedClientId);
    if (selectedClientId === 'inne') {
      console.log(this.trainingForm.value);
      this.data_service.setData({ training: this.trainingForm.value });
      this._router.navigate(['/clients/add']); // Replace '/your-desired-route' with the actual route
    }
  }
}
