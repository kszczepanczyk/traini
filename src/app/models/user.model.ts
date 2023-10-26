import { Training } from './training.model';
export type Trainer = User & {
  tags: SimpleData[];
  localizations: SimpleData[];
};

export type User = {
  id: number;
  photoUrl: string;
  name: string;
  surname: string;
  description: string;
  phone: string;
  city: string;
  gender: string;
  email: string;
};

export type SimpleData = {
  id: number;
  name: string;
};

export type Client = User & {
  trainings: Training[];
  progresses: Progress[];
};

export type Progress = {
  name: string;
  date: string;
  value: number;
  unit: string;
  tendency: boolean;
};
