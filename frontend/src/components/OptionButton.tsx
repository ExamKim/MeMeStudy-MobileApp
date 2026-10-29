import React from 'react';
import { Text, TouchableOpacity, View } from 'react-native';

interface OptionButtonProps {
  index: number;
  text: string;
  isSelected: boolean;
  onSelect: () => void;
}

const OPTION_LETTERS = ['A', 'B', 'C', 'D', 'E', 'F'];

export const OptionButton: React.FC<OptionButtonProps> = ({
  index,
  text,
  isSelected,
  onSelect,
}) => {
  const letter = OPTION_LETTERS[index] || `${index + 1}`;

  return (
    <TouchableOpacity
      onPress={onSelect}
      activeOpacity={0.7}
      className={`flex-row items-center p-4 mb-3 rounded-2xl border-2 transition-all ${
        isSelected
          ? 'bg-blue-50/70 border-blue-600 shadow-sm'
          : 'bg-white border-slate-200'
      }`}
    >
      <View
        className={`w-9 h-9 rounded-xl items-center justify-center mr-3.5 ${
          isSelected ? 'bg-blue-600' : 'bg-slate-100'
        }`}
      >
        <Text
          className={`text-sm font-bold ${
            isSelected ? 'text-white' : 'text-slate-600'
          }`}
        >
          {letter}
        </Text>
      </View>
      <Text
        className={`flex-1 text-base leading-6 font-medium ${
          isSelected ? 'text-blue-900 font-semibold' : 'text-slate-700'
        }`}
      >
        {text}
      </Text>
    </TouchableOpacity>
  );
};

export default OptionButton;

