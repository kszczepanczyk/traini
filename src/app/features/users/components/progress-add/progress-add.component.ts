import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TrainingService } from 'src/app/features/training/training.service';
import { DataService } from 'src/app/shared/data.service';
import { UsersService } from '../../users.service';

@Component({
  selector: 'app-progress-add',
  templateUrl: './progress-add.component.html',
  styleUrls: ['./progress-add.component.scss'],
})
export class ProgressAddComponent {
  progressForm: FormGroup;
  isSubmitted: boolean = false;
  userId: number = null;
  constructor(
    private formBuilder: FormBuilder,
    private data_service: DataService,
    private _router: Router,
    private _activatedRoute: ActivatedRoute,
    private _userService: UsersService
  ) {
    this.progressForm = this.formBuilder.group({
      name: new FormControl('', Validators.required),
      createdAt: new FormControl(new Date(), Validators.required),
      value: new FormControl('', Validators.required),
      unit: new FormControl(1, Validators.required),
      trend: new FormControl('true', Validators.required),
    });
    this._activatedRoute.paramMap.subscribe((params) => {
      this.userId = +params.get('id')!;
    });
  }
  get f(): { [key: string]: AbstractControl } {
    return this.progressForm.controls;
  }

  onSubmitProgress() {
    if (this.progressForm.valid) {
      this._userService
        .addProgress(this.progressForm.value, this.userId)
        .subscribe(
          (res) => {
            console.log('dodane');
          },
          (_) => {
            console.log('error');
          }
        );
    }
  }
}
