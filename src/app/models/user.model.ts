import { Training } from './training.model';
export type Trainer = User & {
  tags: Array<string>;
  locations: Array<string>;
};

export type User = {
  id: number;
  photoB64: string;
  name: string;
  surname: string;
  description: string;
  phone: string;
  city: string;
  gender: string;
  email: string;
};

export type Client = UserListResp & {
  trainingList: Training[];
  progressList: Progress[];
};

export type Progress = {
  progressName: string;
  date: string;
  value: number;
  unit: string;
  trend: boolean;
  progressId: number;
};

export type ProgressList = {
  unit: string;
  trend: boolean;
  progressName: string;
  progressList: [
    {
      progressEntityId: number;
      createdAt: Date;
      value: number;
    }
  ];
};

export type UserListResp = {
  id: number | null;
  name: string;
  surname: string;
  phone: string;
  photo: string;
  city: string;
  description: string;
  gender: string;
};

export type DataToChart = {
  date: string;
  value: number;
};
