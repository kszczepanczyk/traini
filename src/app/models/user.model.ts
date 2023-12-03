import { Training } from './training.model';
export type Trainer = User & {
  tags: [];
  locations: [];
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

export type SimpleData = {
  id: number;
  name: string;
};

export type Client = UserListResp & {
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
