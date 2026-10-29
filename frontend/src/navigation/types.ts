export type AuthStackParamList = {
  Login: undefined;
  Register: undefined;
};

export type UserStackParamList = {
  Home: undefined;
  RoundList: undefined;
  Exam: {
    roundId: number;
    roundTitle: string;
  };
  Result: {
    roundId: number;
    receivedCount?: number;
  };
};

