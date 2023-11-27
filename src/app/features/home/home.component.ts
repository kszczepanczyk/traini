import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as dayjs from 'dayjs';
import { Training } from 'src/app/models/training.model';
import { AuthService } from '../auth/auth.service';
import { HomeService } from './home.service';
import { HomeData } from 'src/app/models/homeData.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  dataLoaded: boolean = false;
  trainingLoaded: boolean = false;
  dates: dayjs.Dayjs[] = [];
  selectedDate: dayjs.Dayjs = dayjs();
  displayedDate: string = 'Dzisiaj ' + dayjs().format('DD.MM.YYYY');
  trainings: Training[] = [];
  numberOfTrainingsToDisplay: number = 3;

  homeData: HomeData;
  error: string = '';
  constructor(private _homeService: HomeService, private _router: Router) {
    this._homeService.getNameAndAvatar().subscribe(
      (res) => {
        this.homeData = res.data;
        this.dataLoaded = true;
      },
      (_) => {
        this.error = 'Coś poszło nie tak';
        this.dataLoaded = true;
      }
    );
  }

  ngOnInit() {
    this._homeService
      .getTrainingsByDate(dayjs().format('DD-MM-YYYY'))
      .subscribe(
        (res) => {
          this.trainings = res.data;
          this.trainingLoaded = true;
          console.log(this.trainings);
        },
        (_) => {
          this.error = 'Błąd przy ładowaniu treningu';
          this.trainingLoaded = true;
        }
      );
    for (let i = 0; i < 6; i++) {
      this.dates.push(dayjs().add(i, 'day'));
    }
  }
  onSelectDate(date: dayjs.Dayjs): void {
    this.numberOfTrainingsToDisplay = 3;
    this.selectedDate = date;
    this.trainingLoaded = false;
    if (this.selectedDate.format('D') === dayjs().format('D')) {
      this.displayedDate = 'Dzisiaj ' + this.selectedDate.format('DD.MM.YYYY');
    } else if (
      this.selectedDate.format('D') === dayjs().add(1, 'day').format('D')
    ) {
      this.displayedDate = 'Jutro ' + this.selectedDate.format('DD.MM.YYYY');
    } else {
      this.displayedDate = this.selectedDate.format('DD.MM.YYYY');
    }
    this._homeService
      .getTrainingsByDate(date.format('DD-MM-YYYY'))
      .subscribe((res) => {
        this.trainings = res.data;
        this.trainingLoaded = true;
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
  navigateToAddTraining() {
    this._router.navigate(['/training/add']);
  }
}
