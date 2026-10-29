export type QuestionType = 'MULTIPLE_CHOICE' | 'LISTENING';

export interface Round {
  id: number;
  roundNumber: number;
  title: string;
  timeLimitSeconds: number;
  maxScore: number;
  questionCount: number;
}

export interface Question {
  id: number;
  content: string;
  type: QuestionType;
  options: string[];
  audioUrl?: string | null;
  orderIndex: number;
}

export interface AnswerItem {
  questionId: number;
  selectedAnswer: string;
}

export interface SubmissionRequest {
  answers: AnswerItem[];
}

export interface SubmissionResponse {
  message: string;
  received: number;
}

export interface User {
  id: number;
  username: string;
  email?: string;
  role: 'USER' | 'ADMIN';
}

