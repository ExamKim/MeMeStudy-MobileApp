import React, { useState, useEffect } from 'react';
import {
  View,
  Text,
  TouchableOpacity,
  SafeAreaView,
  Alert,
  ScrollView,
} from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { UserStackParamList } from '../../navigation/types';
import { roundService } from '../../services/roundService';
import { Question } from '../../types';
import QuestionCard from '../../components/QuestionCard';
import LoadingView from '../../components/LoadingView';
import ErrorView from '../../components/ErrorView';

type Props = NativeStackScreenProps<UserStackParamList, 'Exam'>;

export const ExamScreen: React.FC<Props> = ({ route, navigation }) => {
  const { roundId, roundTitle } = route.params;

  const [questions, setQuestions] = useState<Question[]>([]);
  const [currentIndex, setCurrentIndex] = useState<number>(0);
  const [answers, setAnswers] = useState<Record<number, string>>({});
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState<boolean>(false);

  const fetchQuestions = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await roundService.getRoundQuestions(roundId);
      // Sắp xếp theo orderIndex để đảm bảo thứ tự
      data.sort((a, b) => a.orderIndex - b.orderIndex);
      setQuestions(data);
    } catch (err: unknown) {
      const msg = err instanceof Error ? err.message : 'Không thể tải câu hỏi.';
      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchQuestions();
  }, [roundId]);

  const handleSelectAnswer = (selectedOption: string) => {
    const currentQ = questions[currentIndex];
    if (!currentQ) return;

    setAnswers((prev) => ({
      ...prev,
      [currentQ.id]: selectedOption,
    }));
  };

  const handlePrev = () => {
    if (currentIndex > 0) {
      setCurrentIndex((prev) => prev - 1);
    }
  };

  const handleNext = () => {
    if (currentIndex < questions.length - 1) {
      setCurrentIndex((prev) => prev + 1);
    }
  };

  const handleConfirmExit = () => {
    Alert.alert(
      'Thoát bài thi?',
      'Bài làm hiện tại sẽ không được lưu nếu bạn rời khỏi màn hình.',
      [
        { text: 'Tiếp tục làm', style: 'cancel' },
        { text: 'Thoát', style: 'destructive', onPress: () => navigation.goBack() },
      ]
    );
  };

  const handleSubmit = async () => {
    const answeredCount = Object.keys(answers).length;
    const totalCount = questions.length;

    Alert.alert(
      'Xác nhận nộp bài',
      `Bạn đã hoàn thành ${answeredCount}/${totalCount} câu hỏi. Bạn có chắc chắn muốn nộp bài?`,
      [
        { text: 'Kiểm tra lại', style: 'cancel' },
        {
          text: 'Nộp bài',
          style: 'default',
          onPress: async () => {
            try {
              setSubmitting(true);
              const payload = Object.entries(answers).map(([qId, selected]) => ({
                questionId: Number(qId),
                selectedAnswer: selected,
              }));

              // Gọi API nộp bài (Backend tuần 1 nhận bài & validate input)
              const res = await roundService.submitAnswers(roundId, payload);
              
              Alert.alert('Thành công', `${res.message} (${res.received} câu đã ghi nhận)`, [
                {
                  text: 'Đồng ý',
                  onPress: () =>
                    navigation.navigate('Result', {
                      roundId,
                      receivedCount: res.received,
                    }),
                },
              ]);
            } catch (err: unknown) {
              const msg = err instanceof Error ? err.message : 'Lỗi khi nộp bài.';
              Alert.alert('Lỗi nộp bài', msg);
            } finally {
              setSubmitting(false);
            }
          },
        },
      ]
    );
  };

  if (loading) {
    return <LoadingView message="Đang tải danh sách câu hỏi..." />;
  }

  if (error) {
    return <ErrorView message={error} onRetry={fetchQuestions} />;
  }

  if (questions.length === 0) {
    return (
      <SafeAreaView className="flex-1 bg-slate-50 justify-center items-center p-6">
        <Text className="text-base text-slate-700 font-semibold mb-4">
          Chưa có câu hỏi cho vòng thi này.
        </Text>
        <TouchableOpacity
          onPress={() => navigation.goBack()}
          className="bg-blue-600 px-5 py-2.5 rounded-xl"
        >
          <Text className="text-white font-semibold">Quay lại</Text>
        </TouchableOpacity>
      </SafeAreaView>
    );
  }

  const currentQuestion = questions[currentIndex];
  const total = questions.length;
  const progressPercent = Math.round(((currentIndex + 1) / total) * 100);
  const isFirst = currentIndex === 0;
  const isLast = currentIndex === total - 1;

  return (
    <SafeAreaView className="flex-1 bg-slate-50">
      {/* Top Header */}
      <View className="px-5 py-3 bg-white border-b border-slate-100 flex-row items-center justify-between">
        <TouchableOpacity
          onPress={handleConfirmExit}
          activeOpacity={0.7}
          className="bg-slate-100 px-3 py-1.5 rounded-xl"
        >
          <Text className="text-xs font-bold text-slate-700">✕ Thoát</Text>
        </TouchableOpacity>

        <View className="items-center flex-1 mx-2">
          <Text className="text-xs text-slate-400 font-medium truncate" numberOfLines={1}>
            {roundTitle}
          </Text>
          <Text className="text-sm font-bold text-slate-800">
            Câu {currentIndex + 1} / {total}
          </Text>
        </View>

        {/* TODO(Tuần 2): Đồng hồ đếm ngược */}
        <View className="bg-blue-50 px-3 py-1.5 rounded-xl border border-blue-200">
          <Text className="text-xs font-bold text-blue-700">⏱️ --:--</Text>
        </View>
      </View>

      {/* Progress Bar */}
      <View className="w-full bg-slate-200 h-1.5">
        <View
          className="bg-blue-600 h-1.5 transition-all"
          style={{ width: `${progressPercent}%` }}
        />
      </View>

      {/* Question Content Area */}
      <ScrollView
        className="flex-1 px-5 pt-4"
        contentContainerStyle={{ paddingBottom: 24 }}
        showsVerticalScrollIndicator={false}
      >
        <QuestionCard
          question={currentQuestion}
          questionNumber={currentIndex + 1}
          totalQuestions={total}
          selectedAnswer={answers[currentQuestion.id]}
          onSelectAnswer={handleSelectAnswer}
        />
      </ScrollView>

      {/* Bottom Action Bar */}
      <View className="p-4 bg-white border-t border-slate-100 flex-row items-center justify-between">
        {/* Nút Câu trước */}
        <TouchableOpacity
          onPress={handlePrev}
          disabled={isFirst}
          activeOpacity={0.8}
          className={`px-5 py-3.5 rounded-xl border ${
            isFirst
              ? 'border-slate-200 bg-slate-100 opacity-50'
              : 'border-slate-300 bg-white'
          }`}
        >
          <Text
            className={`text-sm font-bold ${
              isFirst ? 'text-slate-400' : 'text-slate-700'
            }`}
          >
            ← Trước
          </Text>
        </TouchableOpacity>

        {/* Trạng thái đã làm */}
        <View className="items-center">
          <Text className="text-xs text-slate-500 font-medium">
            Đã làm: {Object.keys(answers).length}/{total}
          </Text>
        </View>

        {/* Nút Tiếp theo hoặc Nộp bài */}
        {isLast ? (
          <TouchableOpacity
            onPress={handleSubmit}
            disabled={submitting}
            activeOpacity={0.8}
            className="px-6 py-3.5 rounded-xl bg-emerald-600 shadow-sm"
          >
            <Text className="text-white text-sm font-bold">
              {submitting ? 'Đang nộp...' : 'Nộp bài 📤'}
            </Text>
          </TouchableOpacity>
        ) : (
          <TouchableOpacity
            onPress={handleNext}
            activeOpacity={0.8}
            className="px-6 py-3.5 rounded-xl bg-blue-600 shadow-sm"
          >
            <Text className="text-white text-sm font-bold">Sau →</Text>
          </TouchableOpacity>
        )}
      </View>
    </SafeAreaView>
  );
};

export default ExamScreen;

