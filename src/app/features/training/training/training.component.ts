import { Component, OnInit } from '@angular/core';
import { TrainingService } from '../training.service';
import { Training } from 'src/app/models/training.model';
import { ActivatedRoute, Router } from '@angular/router';
import * as dayjs from 'dayjs';
import { DataService } from 'src/app/shared/data.service';

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
    private _activatedRoute: ActivatedRoute,
    private _router: Router,
    private _dataService: DataService
  ) {
    this._activatedRoute.paramMap.subscribe((params) => {
      this.trainingId = +params.get('id')!;
    });
  }

  ngOnInit(): void {
    this._trainingService.getTraining(this.trainingId).subscribe((res) => {
      this.training = res.data;
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
  deleteTraining() {
    this.isLoaded = false;
    const callbackURL = this._dataService.getData('callbackURL')
      ? this._dataService.getData('callbackURL')
      : '';
    this._trainingService.deleteTraining(this.trainingId).subscribe(
      (res) => {
        this.isLoaded = true;
        this._router.navigate([callbackURL]);
      },
      (err) => {
        (this.error = 'ERROR'), (this.isLoaded = true);
      }
    );
  }
}
