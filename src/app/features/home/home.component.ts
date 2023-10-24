import { Component, OnInit } from '@angular/core';
import * as dayjs from 'dayjs';
import { MockApiService } from 'src/app/mock-api.service';
import { Training } from 'src/app/models/training.model';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
})
export class HomeComponent implements OnInit {
  dates: dayjs.Dayjs[] = [];
  selectedDate: dayjs.Dayjs = dayjs();
  displayedDate: string = 'Dzisiaj ' + dayjs().format('DD.MM.YYYY');
  displayedTrainings: Training[] = [];

  constructor(private _mockService: MockApiService) {}

  ngOnInit() {
    this._mockService.getTrainings(dayjs().format('D')).subscribe((res) => {
      this.displayedTrainings = res;
    });
    for (let i = 0; i < 6; i++) {
      this.dates.push(dayjs().add(i, 'day'));
    }
  }
  onSelectDate(date: dayjs.Dayjs): void {
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
      this.displayedTrainings = res;
    });
  }
}
