'use client';

import React, { useEffect, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import {
  TextField,
  Button,
  Box,
  Paper,
  Typography,
  CircularProgress,
} from '@mui/material';

export default function EditScorePage() {
  const router = useRouter();
  const { scoreId } = useParams(); // /scores/edit/[scoreId]

  const [loading, setLoading] = useState(true);

  // スコア情報のステート
  const [scoreIdValue, setScoreIdValue] = useState(''); 
  const [scoreValue, setScoreValue] = useState('');    
  const [gameDateValue, setGameDateValue] = useState(''); 
  // ここに localStorage の role をセットしたい
  const [updatedBy, setUpdatedBy] = useState('');

  useEffect(() => {
    // (A) ログイン時に localStorage へ保存した role を読み込み
    const storedRole = localStorage.getItem('role');
    if (storedRole) {
      setUpdatedBy(storedRole); 
    }
  }, []);

  useEffect(() => {
    // 初回マウント時にスコアデータを取得してフォームにセット
    const fetchScore = async () => {
      try {
        const res = await fetch(`/api/scores/detail/${scoreId}`);
        if (!res.ok) {
          console.error(`Failed to fetch detail. Status: ${res.status}`);
          setLoading(false);
          return;
        }

        const data = await res.json();
        console.log('Fetched score detail:', data);

        // もし data.score のようにネストされていない場合は修正してください
        if (data?.score) {
          setScoreIdValue(String(data.scoreId));
          setScoreValue(String(data.score));
          setGameDateValue(data.gameDate);
          // updatedBy はローカルストレージから取得したのでここではセットしない
          // もしサーバー上の updatedBy も表示したいなら、別途確かめてください
        } else {
          console.warn('No score found in response:', data);
        }
      } catch (err) {
        console.error('Error fetching score detail:', err);
      } finally {
        setLoading(false);
      }
    };

    if (scoreId) {
      fetchScore();
    }
  }, [scoreId]);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      // PUTリクエストでスコア更新
      const requestBody = {
        scoreId: Number(scoreIdValue),
        score: Number(scoreValue),
        gameDate: gameDateValue,
        // ここで updatedBy にロールを入れる
        updatedBy: updatedBy, 
      };

      const res = await fetch('/api/scores/edit', {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestBody),
      });
      if (!res.ok) {
        const errorText = await res.text();
        alert(`スコア更新に失敗しました: ${errorText}`);
        return;
      }

      alert('スコアを更新しました');
      router.push('/');
    } catch (err) {
      console.error(err);
      alert('スコア更新時にエラーが発生');
    }
  };

  if (loading) {
    return (
      <Box mt={5} display="flex" justifyContent="center">
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box component={Paper} sx={{ p: 2, mt: 2, maxWidth: 400, mx: 'auto' }}>
      <Typography variant="h5" mb={2}>
        スコア編集
      </Typography>

      <form onSubmit={handleSubmit}>
        <TextField
          label="Score ID"
          fullWidth
          margin="normal"
          value={scoreIdValue}
          onChange={(e) => setScoreIdValue(e.target.value)}
          InputProps={{ readOnly: true }}
        />

        <TextField
          label="Score"
          fullWidth
          margin="normal"
          value={scoreValue}
          onChange={(e) => setScoreValue(e.target.value)}
          required
        />

        <TextField
          label="Game Date"
          type="date"
          fullWidth
          margin="normal"
          value={gameDateValue}
          onChange={(e) => setGameDateValue(e.target.value)}
          required
          InputLabelProps={{ shrink: true }}
        />

        {/* updatedBy（ロール）を表示したい場合はテキストフィールドを追加し、readOnlyにしておく */}
        <TextField
          label="Updated By (Role)"
          fullWidth
          margin="normal"
          value={updatedBy}
          onChange={(e) => setUpdatedBy(e.target.value)} // 変えたくないならreadOnlyに
          InputProps={{ readOnly: true }}
        />

        <Box mt={2} display="flex" justifyContent="flex-end">
          <Button type="submit" variant="contained" color="primary">
            更新
          </Button>
        </Box>
      </form>
    </Box>
  );
}
