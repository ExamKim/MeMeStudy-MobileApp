import React from 'react';
import { View, Text } from 'react-native';
import { Question } from '../types';
import OptionButton from './OptionButton';

interface QuestionCardProps {
  question: Question;
  questionNumber: number;
  totalQuestions: number;
  selectedAnswer?: string;
  onSelectAnswer: (answer: string) => void;
}

export const QuestionCard: React.FC<QuestionCardProps> = ({
  question,
  questionNumber,
  totalQuestions,
  selectedAnswer,
  onSelectAnswer,
}) => {
  const isListening = question.type === 'LISTENING';

  return (
    <View className="flex-1">
      {/* Header Info */}
      <View className="flex-row items-center justify-between mb-4">
        <View className="bg-blue-50 px-3.5 py-1.5 rounded-full border border-blue-200">
          <Text className="text-xs font-bold text-blue-700">
            CÂU {questionNumber} / {totalQuestions}
          </Text>
        </View>

        <View
          className={`px-3 py-1.5 rounded-full ${
            isListening ? 'bg-amber-100' : 'bg-slate-100'
          }`}
        >
          <Text
            className={`text-xs font-semibold ${
              isListening ? 'text-amber-800' : 'text-slate-600'
            }`}
          >
            {isListening ? '🎧 BÀI NGHE' : '📝 TRẮC NGHIỆM'}
          </Text>
        </View>
      </View>

      {/* Audio Placeholder (Tuần 2) */}
      {isListening && (
        <View className="bg-amber-50 p-4 rounded-2xl mb-4 border border-amber-200">
          <View className="flex-row items-center justify-between">
            <View className="flex-row items-center flex-1">
              <Text className="text-xl mr-2">🔊</Text>
              <View className="flex-1">
                <Text className="text-xs font-bold text-amber-900">Audio Đính Kèm</Text>
                <Text className="text-xs text-amber-700 truncate" numberOfLines={1}>
                  {question.audioUrl || 'https://audio.memestudy.io/...'}
                </Text>
              </View>
            </View>
            <View className="bg-amber-200/80 px-2.5 py-1 rounded-lg">
              <Text className="text-xs font-semibold text-amber-800">TODO: Tuần 2</Text>
            </View>
          </View>
        </View>
      )}

      {/* Question Content */}
      <View className="bg-white p-5 rounded-2xl mb-5 shadow-sm border border-slate-100">
        <Text className="text-base text-slate-800 leading-7 font-medium">
          {question.content}
        </Text>
      </View>

      {/* Options */}
      <View className="flex-1">
        <Text className="text-xs font-bold uppercase tracking-wider text-slate-400 mb-3 px-1">
          Chọn 1 đáp án đúng:
        </Text>
        {question.options.map((option, index) => (
          <OptionButton
            key={`${question.id}-opt-${index}`}
            index={index}
            text={option}
            isSelected={selectedAnswer === option}
            onSelect={() => onSelectAnswer(option)}
          />
        ))}
      </View>
    </View>
  );
};

export default QuestionCard;

