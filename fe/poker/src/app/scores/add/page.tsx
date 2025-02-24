'use client';

import React, { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';

// MUI
import {
  Typography,
  Box,
  TextField,
  Button,
  Paper,
} from '@mui/material';

const AddScore: React.FC = () => {
  const router = useRouter();

  const [userId, setUserId] = useState('');
  const [score, setScore] = useState('');
  const [gameDate, setGameDate] = useState('');

  // 1) マウント時に localStorage などからユーザーIDを取得
  useEffect(() => {
    const storedUserId = localStorage.getItem('user_id');
    if (storedUserId) {
      setUserId(storedUserId);
    }
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const requestBody = {
        userId: Number(userId),
        score: Number(score),
        gameDate: gameDate,
      };

      const res = await fetch('/api/scores/add', { 
        // 例: next.config.jsで rewrites -> http://localhost:8080/scores/add
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requestBody),
      });
      if (!res.ok) {
        const errorText = await res.text();
        alert(`Failed to add score: ${errorText}`);
        return;
      }
      alert('Score added successfully!');
      router.push('/'); // 一覧に戻る
    } catch (err) {
      console.error(err);
      alert('Error adding score');
    }
  };

  return (
    <Box 
      component={Paper} 
      sx={{ p: 2, mt: 2, maxWidth: 400, mx: 'auto' }}
    >
      <Typography variant="h5" mb={2}>
        スコア追加
      </Typography>
      <form onSubmit={handleSubmit}>
        <TextField
          label="User ID"
          fullWidth
          margin="normal"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          required
          // ユーザーIDを編集不可にしたい場合
          InputProps={{
            readOnly: true,
          }}
        />
        <TextField
          label="Score"
          fullWidth
          margin="normal"
          value={score}
          onChange={(e) => setScore(e.target.value)}
          required
        />
        <TextField
          label="Game Date"
          type="date"
          fullWidth
          margin="normal"
          value={gameDate}
          onChange={(e) => setGameDate(e.target.value)}
          required
          InputLabelProps={{ shrink: true }}
        />

        <Box mt={2} display="flex" justifyContent="flex-end">
          <Button type="submit" variant="contained" color="primary">
            追加
          </Button>
        </Box>
      </form>
    </Box>
  );
};

export default AddScore;
