export type Training = {
  id: number;
  name: string;
  surname: string;
  trainingDate: TrainingDate;
  description: string;
  location: string;
  trainingName: string;
  cycled: boolean;
};

export type TrainingDate = {
  start: string;
  end: string;
};
