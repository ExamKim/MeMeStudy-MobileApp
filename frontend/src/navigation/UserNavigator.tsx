import React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { UserStackParamList } from './types';
import HomeScreen from '../screens/user/HomeScreen';
import RoundListScreen from '../screens/user/RoundListScreen';
import ExamScreen from '../screens/user/ExamScreen';
import ResultScreen from '../screens/user/ResultScreen';

const Stack = createNativeStackNavigator<UserStackParamList>();

export const UserNavigator: React.FC = () => {
  return (
    <Stack.Navigator
      initialRouteName="Home"
      screenOptions={{
        headerBackTitle: 'Quay lại',
        headerTintColor: '#1E293B',
        headerTitleStyle: {
          fontWeight: 'bold',
        },
      }}
    >
      <Stack.Screen
        name="Home"
        component={HomeScreen}
        options={{ headerShown: false }}
      />
      <Stack.Screen
        name="RoundList"
        component={RoundListScreen}
        options={{
          title: 'Danh sách Vòng thi',
          headerShadowVisible: false,
        }}
      />
      <Stack.Screen
        name="Exam"
        component={ExamScreen}
        options={{
          headerShown: false,
          gestureEnabled: false,
        }}
      />
      <Stack.Screen
        name="Result"
        component={ResultScreen}
        options={{
          title: 'Kết quả',
          headerShown: false,
          gestureEnabled: false,
        }}
      />
    </Stack.Navigator>
  );
};

export default UserNavigator;

