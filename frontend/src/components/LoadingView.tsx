import React from 'react';
import { View, Text, ActivityIndicator } from 'react-native';

interface LoadingViewProps {
  message?: string;
}

export const LoadingView: React.FC<LoadingViewProps> = ({ message = 'Đang tải dữ liệu...' }) => {
  return (
    <View className="flex-1 justify-center items-center p-6 bg-slate-50">
      <ActivityIndicator size="large" color="#3B82F6" />
      <Text className="mt-4 text-sm font-medium text-slate-600">{message}</Text>
    </View>
  );
};

export default LoadingView;

