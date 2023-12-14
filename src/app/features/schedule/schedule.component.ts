import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Training } from 'src/app/models/training.model';
import { UsersService } from '../users/users.service';
import * as dayjs from 'dayjs';
import { DataService } from 'src/app/shared/data.service';
import { ScheduleService } from './schedule.service';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.component.html',
  styleUrls: ['./schedule.component.scss'],
})
export class ScheduleComponent {
  trainings: Training[] = [];
  listLoaded: boolean = false;
  error: string = '';
  displayedDate: string = 'Dzisiaj ' + dayjs().format('DD.MM.YYYY');
  constructor(
    private _router: Router,
    private _scheduleService: ScheduleService,
    private data_service: DataService
  ) {}
  ngOnInit() {
    this._scheduleService.getSchedule().subscribe(
      (res) => {
        this.trainings = res.data;
        console.log(this.trainings);
        this.listLoaded = true;
      },
      (_) => {
        this.error = 'Coś poszło nie tak';
        this.listLoaded = true;
      }
    );
  }
  goToTraining(training) {
    this.data_service.setData('callbackURL', '');
    this._router.navigate(['/training', training.id]);
  }
}
