'use client'; // このファイルもクライアントコンポーネント

import React, { useEffect, useState } from 'react';
import { useParams } from 'next/navigation'; // App RouterでURLパラメータを取得
import ScoreLineChart from '../../../components/ScoreLineChart';
import { ScoreData } from '../../types';

const UserDetailsPage: React.FC = () => {
  const [userDetails, setUserDetails] = useState<ScoreData[]>([]);
  
  // [userId] という動的セグメント名に対応
  // URLが /scores/1 の場合、params.userId は "1" となる
  const params = useParams();
  const userId = params.userId;

  useEffect(() => {
    if (!userId) return; // userId が取得できるまで待機

    const fetchUserScores = async () => {
      try {
        // CORSエラーを回避するためには、バックエンドでCORS設定 or rewrites設定が必要
        // ここでは "/api/scores" のプロキシを想定 (next.config.js 参照)
        const res = await fetch(`/api/scores/${userId}`);
        const data = await res.json();
        setUserDetails(data.scoreList);
      } catch (err) {
        console.error('Error fetching user details:', err);
      }
    };

    fetchUserScores();
  }, [userId]);

  if (!userDetails.length) {
    return <div>読み込み中...</div>;
  }

  return (
    <div style={{ padding: '1rem' }}>
      <h2>
        {userDetails[0].username} さんの詳細スコア
      </h2>
      <ScoreLineChart scoreList={userDetails} />
    </div>
  );
};

export default UserDetailsPage;
