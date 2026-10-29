import apiClient from './api';
import { Round, Question, AnswerItem, SubmissionResponse } from '../types';
import { MOCK_ROUNDS, MOCK_QUESTIONS_BY_ROUND } from '../utils/mockData';

const isMock = process.env.EXPO_PUBLIC_USE_MOCK === 'true';

export const roundService = {
  /**
   * Lấy danh sách 3 vòng thi
   */
  async getRounds(): Promise<Round[]> {
    if (isMock) {
      await new Promise((resolve) => setTimeout(resolve, 400));
      return MOCK_ROUNDS;
    }

    try {
      const response = await apiClient.get<Round[]>('/rounds');
      return response.data;
    } catch (error) {
      console.warn('Backend call failed, checking fallback...', error);
      throw error;
    }
  },

  /**
   * Lấy 10 câu hỏi của vòng thi (KHÔNG có đáp án đúng)
   */
  async getRoundQuestions(roundId: number): Promise<Question[]> {
    if (isMock) {
      await new Promise((resolve) => setTimeout(resolve, 400));
      const questions = MOCK_QUESTIONS_BY_ROUND[roundId] || [];
      if (questions.length === 0) {
        throw new Error(`Không tìm thấy câu hỏi cho vòng ${roundId}`);
      }
      return questions;
    }

    const response = await apiClient.get<Question[]>(`/rounds/${roundId}/questions`);
    return response.data;
  },

  /**
   * Nộp bài làm trắc nghiệm
   */
  async submitAnswers(roundId: number, answers: AnswerItem[]): Promise<SubmissionResponse> {
    if (isMock) {
      await new Promise((resolve) => setTimeout(resolve, 500));
      return {
        message: 'Đã nhận bài (Mock mode)',
        received: answers.length,
      };
    }

    const response = await apiClient.post<SubmissionResponse>(`/rounds/${roundId}/submissions`, {
      answers,
    });
    return response.data;
  },
};

export default roundService;

