import React from 'react';
import { View, Text, TouchableOpacity } from 'react-native';

interface ErrorViewProps {
  message?: string;
  onRetry?: () => void;
}

export const ErrorView: React.FC<ErrorViewProps> = ({
  message = 'Đã có lỗi xảy ra khi tải dữ liệu.',
  onRetry,
}) => {
  return (
    <View className="flex-1 justify-center items-center p-6 bg-slate-50">
      <View className="w-16 h-16 bg-red-100 rounded-full items-center justify-center mb-4">
        <Text className="text-2xl text-red-600 font-bold">!</Text>
      </View>
      <Text className="text-base font-semibold text-slate-800 text-center mb-2">Thông báo</Text>
      <Text className="text-sm text-slate-500 text-center mb-6 leading-5">{message}</Text>
      {onRetry && (
        <TouchableOpacity
          onPress={onRetry}
          activeOpacity={0.8}
          className="bg-blue-600 px-6 py-3 rounded-xl shadow-sm"
        >
          <Text className="text-white text-sm font-semibold">Thử lại</Text>
        </TouchableOpacity>
      )}
    </View>
  );
};

export default ErrorView;

