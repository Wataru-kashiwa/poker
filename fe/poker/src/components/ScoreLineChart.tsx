'use client'; // このファイルはクライアントコンポーネントとして扱う

import React from 'react';
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  ResponsiveContainer,
} from 'recharts';
import { ScoreData } from '../app/types';

interface ScoreLineChartProps {
  scoreList: ScoreData[];
}

const ScoreLineChart: React.FC<ScoreLineChartProps> = ({ scoreList }) => {
  // Rechartsに渡すデータをmapで生成
  // { name: 日付, value: スコア } など任意のキー名に変換してOKです
  const chartData = scoreList.map((item) => ({
    date: item.gameDate,
    score: item.score,
  }));

  return (
    <ResponsiveContainer width="100%" height={400}>
      <LineChart data={chartData}>
        <CartesianGrid strokeDasharray="3 3" />
        {/* X軸に日付を表示 */}
        <XAxis dataKey="date" />
        {/* Y軸にスコアを表示 */}
        <YAxis />
        {/* ツールチップ、凡例 */}
        <Tooltip />
        <Legend />
        {/* 折れ線 */}
        <Line type="monotone" dataKey="score" stroke="#8884d8" />
      </LineChart>
    </ResponsiveContainer>
  );
};

export default ScoreLineChart;
