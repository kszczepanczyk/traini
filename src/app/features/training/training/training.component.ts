import { Component, OnInit } from '@angular/core';
import { TrainingService } from '../training.service';
import { Training } from 'src/app/models/training.model';
import { ActivatedRoute } from '@angular/router';

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
