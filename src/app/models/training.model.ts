export type Training = {
  name: string;
  surname: string;
  location: string;
  cycled: boolean;
  trainingDate: TrainingDate;
  trainingName: string;
  id: number;
  //TODO:
  //delete
  date: string;
  timeFrom: string;
  timeTo: string;
  client: string;
  place: string;
  type: string;
  description: string | null;
};

export type TrainingDate = {
  start: string;
  end: string;
};

export type TrainingSchedule = {
  createdAt: string;
  cycled: boolean;
  description: string;
  id: number;
  locationId: number;
  name: string;
  trainerId: number;
  trainingDateEnd: string;
  trainingDateStart: string;
  userId: number;
};
