import React, { useState, useEffect } from 'react';
import { View, Text, FlatList, SafeAreaView, TouchableOpacity } from 'react-native';
import { NativeStackScreenProps } from '@react-navigation/native-stack';
import { UserStackParamList } from '../../navigation/types';
import { roundService } from '../../services/roundService';
import { Round } from '../../types';
import RoundCard from '../../components/RoundCard';
import LoadingView from '../../components/LoadingView';
import ErrorView from '../../components/ErrorView';

type Props = NativeStackScreenProps<UserStackParamList, 'RoundList'>;

export const RoundListScreen: React.FC<Props> = ({ navigation }) => {
  const [rounds, setRounds] = useState<Round[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  const fetchRounds = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await roundService.getRounds();
      setRounds(data);
    } catch (err: unknown) {
      const msg = err instanceof Error ? err.message : 'Không thể kết nối đến máy chủ.';
      setError(msg);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchRounds();
  }, []);

  if (loading) {
    return <LoadingView message="Đang tải danh sách vòng thi..." />;
  }

  if (error) {
    return <ErrorView message={error} onRetry={fetchRounds} />;
  }

  return (
    <SafeAreaView className="flex-1 bg-slate-50">
      <View className="flex-1 px-5 pt-4">
        {/* Header */}
        <View className="flex-row items-center justify-between mb-4">
          <View>
            <Text className="text-xs font-semibold text-slate-400 uppercase tracking-wider">
              Chọn vòng thi
            </Text>
            <Text className="text-2xl font-extrabold text-slate-800">
              Danh sách Vòng thi
            </Text>
          </View>

          <TouchableOpacity
            onPress={fetchRounds}
            activeOpacity={0.7}
            className="bg-blue-50 px-3 py-1.5 rounded-xl border border-blue-200"
          >
            <Text className="text-xs font-semibold text-blue-600">Làm mới 🔄</Text>
          </TouchableOpacity>
        </View>

        {/* Empty State */}
        {rounds.length === 0 ? (
          <View className="flex-1 justify-center items-center p-6">
            <Text className="text-base font-semibold text-slate-600 mb-2">
              Hiện chưa có vòng thi nào đang mở.
            </Text>
            <TouchableOpacity
              onPress={fetchRounds}
              className="bg-blue-600 px-5 py-2.5 rounded-xl"
            >
              <Text className="text-white text-sm font-semibold">Tải lại</Text>
            </TouchableOpacity>
          </View>
        ) : (
          <FlatList
            data={rounds}
            keyExtractor={(item) => item.id.toString()}
            showsVerticalScrollIndicator={false}
            contentContainerStyle={{ paddingBottom: 24 }}
            renderItem={({ item }) => (
              <RoundCard
                round={item}
                onPress={() =>
                  navigation.navigate('Exam', {
                    roundId: item.id,
                    roundTitle: item.title,
                  })
                }
              />
            )}
          />
        )}
      </View>
    </SafeAreaView>
  );
};

export default RoundListScreen;

