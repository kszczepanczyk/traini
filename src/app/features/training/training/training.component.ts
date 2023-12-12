import { Component, OnInit } from '@angular/core';
import { TrainingService } from '../training.service';
import { Training } from 'src/app/models/training.model';
import { ActivatedRoute } from '@angular/router';
import * as dayjs from 'dayjs';

@Component({
  selector: 'app-training',
  templateUrl: './training.component.html',
  styleUrls: ['./training.component.scss'],
})
export class TrainingComponent implements OnInit {
  training: Training;
  trainingId: number;
  isLoaded: boolean = false;
  error: string = '';
  date = {
    day: '',
    time: '',
  };

  constructor(
    private _trainingService: TrainingService,
    private _activatedRoute: ActivatedRoute
  ) {
    this._activatedRoute.paramMap.subscribe((params) => {
      this.trainingId = +params.get('id')!;
    });
  }

  ngOnInit(): void {
    this._trainingService.getTraining(this.trainingId).subscribe((res) => {
      this.training = res.data;
      console.log(dayjs(res.data.trainingDate.start));
      this.date = {
        day: dayjs(res.data.trainingDate.start).format('DD.MM.YYYY'),
        time:
          dayjs(res.data.trainingDate.start).format('HH:mm') +
          ' - ' +
          dayjs(res.data.trainingDate.end).format('HH:mm'),
      };
      this.isLoaded = true;
    });
  }

  goToEdit() {
    throw new Error('Method not implemented.');
  }
  deleteClient() {
    throw new Error('Method not implemented.');
  }
}
