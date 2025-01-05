'use client';

import React, { useEffect, useState } from 'react';
import Link from 'next/link';
import { Score } from './types';

// Material UI
import {
  Typography,
  Box,
  TableContainer,
  Paper,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  CircularProgress,
  Button,
} from '@mui/material';

const Home: React.FC = () => {
  const [scoreList, setScoreList] = useState<Score[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const res = await fetch('/api/scores'); // プロキシ経由でリクエスト
        const data = await res.json();
        setScoreList(data.scoreList);
      } catch (err) {
        console.error('Error fetching scores:', err);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, []);

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" mt={5}>
        <CircularProgress />
      </Box>
    );
  }

  return (
    <Box sx={{ p: 2 }}>
      <Typography variant="h4" gutterBottom>
        ポーカースコア一覧
      </Typography>
      
      <TableContainer component={Paper}>
        <Table>
          <TableHead sx={{ backgroundColor: '#F1F3F4' /* Googleっぽい薄いグレー */ }}>
            <TableRow>
              <TableCell>ユーザー名</TableCell>
              <TableCell>ゲーム日付</TableCell>
              <TableCell>スコア</TableCell>
              <TableCell>更新日</TableCell>
              <TableCell align="center">詳細</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {scoreList.map((score) => (
              <TableRow key={score.scoreId}>
                <TableCell>{score.username}</TableCell>
                <TableCell>{score.gameDate}</TableCell>
                <TableCell>{score.score}</TableCell>
                <TableCell>{score.updatedAt}</TableCell>
                <TableCell align="center">
                  {/* Next.js の <Link> だけでもOKですが、MUIのボタンで装飾 */}
                  <Button variant="outlined" color="primary">
                    <Link 
                      href={`/scores/${score.userId}`} 
                      style={{ textDecoration: 'none', color: 'inherit' }}
                    >
                      詳細
                    </Link>
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
    </Box>
  );
};

export default Home;
