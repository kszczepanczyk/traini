import { Component } from '@angular/core';
import { UsersService } from '../../users.service';
import { ActivatedRoute } from '@angular/router';
import { DataToChart, ProgressList } from 'src/app/models/user.model';
import * as dayjs from 'dayjs';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';

@Component({
  selector: 'app-progress',
  templateUrl: './progress.component.html',
  styleUrls: ['./progress.component.scss'],
})
export class ProgressComponent {
  isModalVisible: boolean = false;
  isLoaded: boolean = false;
  isSubmitted: boolean = false;
  error: string = '';
  progressForm: FormGroup;
  progressReq = {
    idUser: null,
    idProgress: null,
  };
  dataToChart: Array<DataToChart> = [];
  progressData: ProgressList;
  best: number;
  latest: number;
  difference: number;
  sign: string;
  constructor(
    private _userService: UsersService,
    private _activatedRoute: ActivatedRoute,
    private formBuilder: FormBuilder
  ) {
    this._activatedRoute.paramMap.subscribe((params) => {
      this.progressReq.idUser = +params.get('id');
      this.progressReq.idProgress = +params.get('progress_id');
    });
    this._userService.getProgressEntity(this.progressReq).subscribe(
      (res) => {
        this.progressData = res.data;
        if (this.progressData.trend) {
          this.best = Math.max(
            ...this.progressData.progressList.map((item) => item.value)
          );
        } else {
          this.best = Math.min(
            ...this.progressData.progressList.map((item) => item.value)
          );
        }
        const latestProgress = this.progressData.progressList.reduce(
          (latest, current) => {
            const currentCreatedAt = new Date(current.createdAt).getTime();
            const latestCreatedAt = latest
              ? new Date(latest.createdAt).getTime()
              : 0;

            return currentCreatedAt > latestCreatedAt ? current : latest;
          },
          null
        );
        this.latest = latestProgress ? latestProgress.value : null;
        this.difference = this.best - this.latest;
        this.progressData.progressList.forEach((item) => {
          this.dataToChart.push({
            date: dayjs(item.createdAt).format('DD.MM.YYYY'),
            value: item.value,
          });
        });
        this.progressData.progressList.reverse();
        if (this.progressData.trend) {
          this.sign = this.difference > 0 ? '-' : '+';
        } else {
          this.sign = this.difference > 0 ? '+' : '-';
        }
        this.isLoaded = true;
      },
      (err) => {
        console.log('err');
        this.isLoaded = true;
      }
    );
    this.progressForm = this.formBuilder.group({
      createdAt: new FormControl(new Date(), Validators.required),
      value: new FormControl('', Validators.required),
    });
  }

  get f(): { [key: string]: AbstractControl } {
    return this.progressForm.controls;
  }

  deleteProgress() {}
  deleteProgressEntity(id: number) {}
  addProgressEntity() {
    if (this.progressForm.valid) {
      const { value, createdAt } = this.progressForm.value;
      this._userService.addProgress(
        {
          name: this.progressData.progressName,
          trend: this.progressData.trend,
          unit: this.progressData.unit,
          value: value,
          createdAt: createdAt,
        },
        this.progressReq.idUser
      );
    }
  }
  closeModal() {
    this.isModalVisible = false;
  }
  openModal() {
    this.isModalVisible = true;
  }
}
