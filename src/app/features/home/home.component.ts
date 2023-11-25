import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as dayjs from 'dayjs';
import { MockApiService } from 'src/app/mock-api.service';
import { Training } from 'src/app/models/training.model';
import { AuthService } from '../auth/auth.service';
import { HomeService } from './home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  dates: dayjs.Dayjs[] = [];
  selectedDate: dayjs.Dayjs = dayjs();
  displayedDate: string = 'Dzisiaj ' + dayjs().format('DD.MM.YYYY');
  trainings: Training[] = [];
  numberOfTrainingsToDisplay: number = 3;
  constructor(
    private _mockService: MockApiService,
    private _homeService: HomeService,
    private _router: Router,
    private _auth: AuthService
  ) {}

  ngOnInit() {
    this._mockService.getTrainings(dayjs().format('D')).subscribe((res) => {
      this.trainings = res;
    });
    for (let i = 0; i < 6; i++) {
      this.dates.push(dayjs().add(i, 'day'));
    }
  }
  onSelectDate(date: dayjs.Dayjs): void {
    this.numberOfTrainingsToDisplay = 3;
    this.selectedDate = date;
    if (this.selectedDate.format('D') === dayjs().format('D')) {
      this.displayedDate = 'Dzisiaj ' + this.selectedDate.format('DD.MM.YYYY');
    } else if (
      this.selectedDate.format('D') === dayjs().add(1, 'day').format('D')
    ) {
      this.displayedDate = 'Jutro ' + this.selectedDate.format('DD.MM.YYYY');
    } else {
      this.displayedDate = this.selectedDate.format('DD.MM.YYYY');
    }
    this._mockService.getTrainings(date.format('D')).subscribe((res) => {
      this.trainings = res;
    });
  }

  getDisplayedTrainings(): Training[] {
    return this.trainings.slice(0, this.numberOfTrainingsToDisplay);
  }

  loadMore() {
    this.numberOfTrainingsToDisplay += 3;
  }
  navigateToProfile() {
    this._router.navigate(['/profile']);
  }
}
