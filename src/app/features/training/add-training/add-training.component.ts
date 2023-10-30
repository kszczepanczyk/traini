import { Component } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
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
  constructor(
    private formBuilder: FormBuilder,
    private data_service: DataService,
    private api_service: MockApiService
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
      details: new FormControl(''),
      localization: new FormControl(''),
    });
  }

  goBack() {}
  onSubmitTraining() {}
}
