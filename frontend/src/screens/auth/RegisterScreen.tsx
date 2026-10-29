import React from 'react';
import { View, Text, TouchableOpacity, SafeAreaView } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { AuthStackParamList } from '../../navigation/types';

type Props = NativeStackScreenProps<AuthStackParamList, 'Register'>;

export const RegisterScreen: React.FC<Props> = ({ navigation }) => {
  return (
    <SafeAreaView className="flex-1 bg-white">
      <View className="flex-1 justify-center px-6">
        <View className="items-center mb-8">
          <Text className="text-2xl font-bold text-slate-800">Đăng ký tài khoản</Text>
          <Text className="text-sm text-slate-500 mt-1">Placeholder - Do Người 1 phụ trách</Text>
        </View>

        <TouchableOpacity
          onPress={() => navigation.navigate('Login')}
          activeOpacity={0.8}
          className="bg-blue-600 py-3.5 rounded-xl items-center justify-center shadow-sm"
        >
          <Text className="text-white text-base font-semibold">Quay lại Đăng nhập</Text>
        </TouchableOpacity>
      </View>
    </SafeAreaView>
  );
};

export default RegisterScreen;

