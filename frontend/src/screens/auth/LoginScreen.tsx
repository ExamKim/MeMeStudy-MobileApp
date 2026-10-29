import React from 'react';
import { View, Text, TouchableOpacity, SafeAreaView } from 'react-native';
import { useAuth } from '../../context/AuthContext';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { AuthStackParamList } from '../../navigation/types';

type Props = NativeStackScreenProps<AuthStackParamList, 'Login'>;

export const LoginScreen: React.FC<Props> = ({ navigation }) => {
  const { login } = useAuth();

  return (
    <SafeAreaView className="flex-1 bg-white">
      <View className="flex-1 justify-center px-6">
        <View className="items-center mb-8">
          <Text className="text-4xl mb-2">🎯</Text>
          <Text className="text-2xl font-bold text-slate-800">MeMeStudy</Text>
          <Text className="text-sm text-slate-500 mt-1">Đăng nhập tài khoản người học</Text>
        </View>

        <View className="bg-amber-50 p-4 rounded-2xl mb-6 border border-amber-200">
          <Text className="text-xs font-semibold text-amber-800 mb-1">
            📌 Module Auth do Khánh (Người 1) phụ trách
          </Text>
          <Text className="text-xs text-amber-700">
            Màn hình này là placeholder. Bạn có thể nhấn nút bên dưới để bỏ qua đăng nhập và test luồng thi.
          </Text>
        </View>

        <TouchableOpacity
          onPress={login}
          activeOpacity={0.8}
          className="bg-blue-600 py-3.5 rounded-xl items-center justify-center shadow-sm mb-3"
        >
          <Text className="text-white text-base font-semibold">Vào học ngay (Dev Bypass)</Text>
        </TouchableOpacity>

        <TouchableOpacity
          onPress={() => navigation.navigate('Register')}
          activeOpacity={0.7}
          className="py-3 items-center"
        >
          <Text className="text-sm text-blue-600 font-medium">Chưa có tài khoản? Đăng ký</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

export default LoginScreen;

