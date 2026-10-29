import React from 'react';
import { View, Text, TouchableOpacity, SafeAreaView } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { UserStackParamList } from '../../navigation/types';

type Props = NativeStackScreenProps<UserStackParamList, 'Result'>;

export const ResultScreen: React.FC<Props> = ({ route, navigation }) => {
  const { roundId, receivedCount = 10 } = route.params;

  return (
    <SafeAreaView className="flex-1 bg-slate-50">
      <View className="flex-1 justify-center items-center px-6">
        <View className="w-20 h-20 bg-emerald-100 rounded-full items-center justify-center mb-6">
          <Text className="text-4xl">🎉</Text>
        </View>

        <Text className="text-2xl font-extrabold text-slate-800 text-center mb-2">
          Nộp bài thành công!
        </Text>

        <Text className="text-sm text-slate-500 text-center mb-6 leading-6">
          Hệ thống máy chủ đã ghi nhận {receivedCount} câu trả lời cho Vòng {roundId}.
        </Text>

        {/* TODO(Tuần 2) Box */}
        <View className="bg-blue-50 border border-blue-200 p-4 rounded-2xl mb-8 w-full">
          <Text className="text-xs font-bold text-blue-800 mb-1">
            📌 Kế hoạch Tuần 2:
          </Text>
          <Text className="text-xs text-blue-700 leading-5">
            - Chấm điểm tự động trên server và lưu lịch sử kết quả thi.{'\n'}
            - Hiển thị bảng tổng kết điểm số, thời gian làm bài và chi tiết đúng/sai từng câu.
          </Text>
        </View>

        <TouchableOpacity
          onPress={() => navigation.navigate('Home')}
          activeOpacity={0.8}
          className="bg-blue-600 w-full py-3.5 rounded-xl items-center justify-center shadow-sm mb-3"
        >
          <Text className="text-white text-base font-semibold">Về trang chủ</Text>
        </TouchableOpacity>

        <TouchableOpacity
          onPress={() => navigation.navigate('RoundList')}
          activeOpacity={0.7}
          className="py-3 items-center"
        >
          <Text className="text-sm text-blue-600 font-medium">Chọn vòng thi khác</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

export default ResultScreen;

