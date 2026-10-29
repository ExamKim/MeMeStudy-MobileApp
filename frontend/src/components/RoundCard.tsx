import React from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import { Round } from '../types';

interface RoundCardProps {
  round: Round;
  onPress: () => void;
}

export const RoundCard: React.FC<RoundCardProps> = ({ round, onPress }) => {
  const minutes = Math.round(round.timeLimitSeconds / 60);

  const getBadgeColor = (roundNum: number) => {
    switch (roundNum) {
      case 1:
        return 'bg-blue-100 text-blue-700';
      case 2:
        return 'bg-amber-100 text-amber-700';
      case 3:
        return 'bg-purple-100 text-purple-700';
      default:
        return 'bg-slate-100 text-slate-700';
    }
  };

  return (
    <View className="bg-white rounded-2xl p-5 mb-4 shadow-sm border border-slate-100">
      <View className="flex-row items-center justify-between mb-3">
        <View className={`px-3 py-1 rounded-full ${getBadgeColor(round.roundNumber)}`}>
          <Text className="text-xs font-bold uppercase tracking-wider">
            VÒNG {round.roundNumber}
          </Text>
        </View>
        <Text className="text-xs font-medium text-slate-400">Tối đa: {round.maxScore} điểm</Text>
      </View>

      <Text className="text-lg font-bold text-slate-800 mb-2">{round.title}</Text>

      <View className="flex-row items-center space-x-4 mb-4 pt-2 border-t border-slate-100">
        <View className="flex-row items-center mr-4">
          <Text className="text-xs text-slate-500 font-medium">📝 {round.questionCount} câu hỏi</Text>
        </View>
        <View className="flex-row items-center mr-4">
          <Text className="text-xs text-slate-500 font-medium">⏱️ {minutes} phút</Text>
        </View>
        <View className="flex-row items-center">
          <Text className="text-xs text-slate-500 font-medium">🎯 {round.maxScore} điểm</Text>
        </View>
      </View>

      <TouchableOpacity
        onPress={onPress}
        activeOpacity={0.8}
        className="bg-blue-600 w-full py-3.5 rounded-xl items-center justify-center shadow-sm"
      >
        <Text className="text-white text-sm font-semibold">Vào làm bài</Text>
      </TouchableOpacity>
    </View>
  );
};

export default RoundCard;

