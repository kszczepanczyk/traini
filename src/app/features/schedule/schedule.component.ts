import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Training } from 'src/app/models/training.model';
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
  groupedTrainings: any[] = [];
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
        this.groupedTrainings = this.groupAndSortByDate(this.trainings);
        this.listLoaded = true;
      },
      (_) => {
        this.error = 'Coś poszło nie tak';
        this.listLoaded = true;
      }
    );
  }
  goToTraining(training) {
    this.data_service.setData('callbackURL', '/schedule');
    this._router.navigate(['/training', training.id]);
  }
  generateCyclingOccurrences(training: any): any[] {
    const occurrences = [];
    for (let i = 1; i <= 2; i++) {
      const occurrence = structuredClone(training);
      occurrence.trainingDate.start = dayjs(occurrence.trainingDate.start)
        .add(7 * i, 'day')
        .toISOString();
      occurrence.trainingDate.end = dayjs(occurrence.trainingDate.end)
        .add(7 * i, 'day')
        .toISOString();
      occurrences.push({ ...occurrence });
    }
    return occurrences;
  }

  groupAndSortByDate(trainings: any[]): any[] {
    const grouped = {};
    let modifiedTrainings = trainings;
    trainings.forEach((training) => {
      if (training.cycled) {
        const occurrences = this.generateCyclingOccurrences(training);
        modifiedTrainings.push(...occurrences);
      }
    });
    modifiedTrainings.forEach((training) => {
      const date = training.trainingDate.start.split('T')[0];
      if (!grouped[date]) {
        grouped[date] = [];
      }
      grouped[date].push(training);
    });

    Object.keys(grouped).forEach((date) => {
      grouped[date] = grouped[date].sort((a, b) => {
        const timeA = dayjs(a.trainingDate.start).valueOf();
        const timeB = dayjs(b.trainingDate.start).valueOf();
        return timeA - timeB;
      });
    });

    const finalGrouped = Object.keys(grouped).map((date) => ({
      date,
      trainings: grouped[date],
    }));

    finalGrouped.sort((a, b) => {
      const dateA = dayjs(a.date).valueOf();
      const dateB = dayjs(b.date).valueOf();
      return dateA - dateB;
    });

    return finalGrouped;
  }
}
