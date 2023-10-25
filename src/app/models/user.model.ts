export type User = {
  photoUrl: string;
  name: string;
  surname: string;
  description: string;
  phone: string;
  city: string;
  gender: string;
  tags: SimpleData[];
  localizations: SimpleData[];
  email: string;
};

export type SimpleData = {
  id: number;
  name: string;
};
