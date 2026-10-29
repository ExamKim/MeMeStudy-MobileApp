import React from 'react';
import { View, Text, TouchableOpacity, ScrollView, SafeAreaView } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { UserStackParamList } from '../../navigation/types';
import { useAuth } from '../../context/AuthContext';

type Props = NativeStackScreenProps<UserStackParamList, 'Home'>;

export const HomeScreen: React.FC<Props> = ({ navigation }) => {
  const { logout } = useAuth();
  const isMock = process.env.EXPO_PUBLIC_USE_MOCK === 'true';

  return (
    <SafeAreaView className="flex-1 bg-slate-50">
      <ScrollView className="flex-1 px-5 pt-3" showsVerticalScrollIndicator={false}>
        {/* Top Header */}
        <View className="flex-row items-center justify-between mb-5">
          <View>
            <Text className="text-xs font-semibold text-slate-400 uppercase tracking-wider">
              Hệ thống thi tiếng Anh
            </Text>
            <Text className="text-2xl font-extrabold text-slate-800">
              Chào bạn! 👋
            </Text>
          </View>

          <TouchableOpacity
            onPress={logout}
            activeOpacity={0.7}
            className="bg-slate-200/80 px-3 py-1.5 rounded-full"
          >
            <Text className="text-xs font-semibold text-slate-600">Đăng xuất</Text>
          </TouchableOpacity>
        </View>

        {/* Mock Mode Indicator */}
        {isMock && (
          <View className="bg-amber-50 border border-amber-200 p-3 rounded-xl mb-4">
            <Text className="text-xs font-semibold text-amber-800">
              ⚠️ Đang chạy chế độ Mock Data (EXPO_PUBLIC_USE_MOCK=true)
            </Text>
          </View>
        )}

        {/* Hero Banner Card */}
        <View className="bg-blue-600 rounded-3xl p-6 mb-6 shadow-md shadow-blue-500/20">
          <View className="bg-blue-500/40 self-start px-3 py-1 rounded-full mb-3">
            <Text className="text-xs font-bold text-white uppercase tracking-wider">
              Kỳ thi Quốc gia 2026
            </Text>
          </View>

          <Text className="text-xl font-bold text-white mb-2 leading-7">
            Mô phỏng Đề thi IOE Chuẩn Quốc Gia
          </Text>
          <Text className="text-blue-100 text-sm mb-5 leading-5">
            Trải nghiệm 3 vòng thi liên hoàn: Khởi động, Tăng tốc và Về đích với tổng cộng 30 câu hỏi chuẩn hóa.
          </Text>

          <TouchableOpacity
            onPress={() => navigation.navigate('RoundList')}
            activeOpacity={0.85}
            className="bg-white py-3.5 px-6 rounded-2xl items-center justify-center shadow-sm"
          >
            <Text className="text-blue-700 text-base font-bold">
              Bắt đầu thi ngay 🚀
            </Text>
          </TouchableOpacity>
        </View>

        {/* 3 Rounds Summary */}
        <View className="mb-6">
          <View className="flex-row items-center justify-between mb-3">
            <Text className="text-base font-bold text-slate-800">
              Cấu trúc 3 vòng thi
            </Text>
            <TouchableOpacity onPress={() => navigation.navigate('RoundList')}>
              <Text className="text-xs font-semibold text-blue-600">Xem tất cả</Text>
            </TouchableOpacity>
          </View>

          {/* Round 1 Mini Card */}
          <View className="bg-white p-4 rounded-2xl mb-3 border border-slate-100 flex-row items-center justify-between">
            <View className="flex-row items-center flex-1">
              <View className="w-10 h-10 rounded-xl bg-blue-100 items-center justify-center mr-3">
                <Text className="text-base font-bold text-blue-700">1</Text>
              </View>
              <View className="flex-1">
                <Text className="text-sm font-bold text-slate-800">Vòng 1: Khởi động</Text>
                <Text className="text-xs text-slate-400 mt-0.5">10 câu hỏi trắc nghiệm & nghe • 10 phút</Text>
              </View>
            </View>
            <View className="bg-slate-50 px-2.5 py-1 rounded-lg">
              <Text className="text-xs font-semibold text-slate-600">100 điểm</Text>
            </View>
          </View>

          {/* Round 2 Mini Card */}
          <View className="bg-white p-4 rounded-2xl mb-3 border border-slate-100 flex-row items-center justify-between">
            <View className="flex-row items-center flex-1">
              <View className="w-10 h-10 rounded-xl bg-amber-100 items-center justify-center mr-3">
                <Text className="text-base font-bold text-amber-700">2</Text>
              </View>
              <View className="flex-1">
                <Text className="text-sm font-bold text-slate-800">Vòng 2: Tăng tốc</Text>
                <Text className="text-xs text-slate-400 mt-0.5">10 câu hỏi tăng dần độ khó • 15 phút</Text>
              </View>
            </View>
            <View className="bg-slate-50 px-2.5 py-1 rounded-lg">
              <Text className="text-xs font-semibold text-slate-600">100 điểm</Text>
            </View>
          </View>

          {/* Round 3 Mini Card */}
          <View className="bg-white p-4 rounded-2xl mb-3 border border-slate-100 flex-row items-center justify-between">
            <View className="flex-row items-center flex-1">
              <View className="w-10 h-10 rounded-xl bg-purple-100 items-center justify-center mr-3">
                <Text className="text-base font-bold text-purple-700">3</Text>
              </View>
              <View className="flex-1">
                <Text className="text-sm font-bold text-slate-800">Vòng 3: Về đích</Text>
                <Text className="text-xs text-slate-400 mt-0.5">10 câu hỏi nâng cao & phân hóa • 20 phút</Text>
              </View>
            </View>
            <View className="bg-slate-50 px-2.5 py-1 rounded-lg">
              <Text className="text-xs font-semibold text-slate-600">100 điểm</Text>
            </View>
          </View>
        </View>

        {/* Security / System Notice */}
        <View className="bg-slate-100 p-4 rounded-2xl mb-8">
          <Text className="text-xs font-bold text-slate-700 mb-1">
            🔒 Quy chế thi bảo mật
          </Text>
          <Text className="text-xs text-slate-500 leading-4">
            Đáp án đúng được bảo mật trên máy chủ và chỉ được chấm điểm khi thí sinh nộp bài. Thiết bị di động không lưu đáp án đúng.
          </Text>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

export default HomeScreen;

