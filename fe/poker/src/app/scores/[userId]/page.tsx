'use client';

import React, { useEffect, useState } from 'react';
import { useParams } from 'next/navigation';
import { ScoreData } from '../../types';
import ScoreLineChart from '../../../components/ScoreLineChart';
import { 
  Box, 
  Table, 
  TableBody, 
  TableCell, 
  TableContainer, 
  TableHead, 
  TableRow, 
  Paper, 
  Button, 
  Link, 
  CircularProgress
} from '@mui/material';

const UserDetailsPage: React.FC = () => {
  const [userDetails, setUserDetails] = useState<ScoreData[]>([]);
  const [loading, setLoading] = useState(true);
  const [loggedInUserId, setLoggedInUserId] = useState<string | null>(null);

  const params = useParams();
  const userId = params.userId; // URLの [userId]（文字列）

  useEffect(() => {
    // ローカルストレージからログイン中ユーザーのIDを取得
    const storedUserId = localStorage.getItem('user_id');
    if (storedUserId) {
      setLoggedInUserId(storedUserId);
    }
  }, []);

  useEffect(() => {
    if (!userId) return;

    const fetchUserScores = async () => {
      try {
        const res = await fetch(`/api/scores/${userId}`);
        const data = await res.json();
        setUserDetails(data.scoreList);
      } catch (err) {
        console.error('Error fetching user details:', err);
      } finally {
        setLoading(false);
      }
    };
    fetchUserScores();
  }, [userId]);

  if (loading) {
    return (
      <Box display="flex" justifyContent="center" alignItems="center" mt={5}>
        <CircularProgress />
      </Box>
    );
  }

  if (userDetails.length === 0) {
    return <div>スコアがありません</div>;
  }

  // ログイン中ユーザーIDとこのページの userId を比較
  const isSameUser = loggedInUserId === userId;

  return (
    <Box sx={{ p: 2 }}>
      <h2>{userDetails[0].username} さんの詳細スコア</h2>

      {/* 折れ線グラフ表示 */}
      <ScoreLineChart scoreList={userDetails} />

      {/* スコア一覧をテーブルで表示 */}
      <TableContainer component={Paper} sx={{ mt: 3 }}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell>スコアID</TableCell>
              <TableCell>ゲーム日付</TableCell>
              <TableCell>スコア</TableCell>
              <TableCell>更新日</TableCell>
              {/* ログイン中ユーザーなら「編集」列を表示 */}
              {isSameUser && <TableCell>編集</TableCell>}
            </TableRow>
          </TableHead>
          <TableBody>
            {userDetails.map((score) => (
              <TableRow key={score.scoreId}>
                <TableCell>{score.scoreId}</TableCell>
                <TableCell>{score.gameDate}</TableCell>
                <TableCell>{score.score}</TableCell>
                <TableCell>{score.updatedAt}</TableCell>
                {isSameUser && (
                  <TableCell>
                    {/* 「編集」ボタン → /scores/edit/[scoreId] */}
                    <Button variant="outlined" color="primary">
                      <Link
                        href={`/scores/edit/${score.scoreId}`}
                        style={{ textDecoration: 'none', color: 'inherit' }}
                      >
                        編集
                      </Link>
                    </Button>
                  </TableCell>
                )}
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>

      {/* ログイン中ユーザーIDと一致する場合のみ「スコア追加」ボタンも表示 */}
      {isSameUser && (
        <Box mt={2}>
          <Button variant="contained">
            <Link
              href={`/scores/add`}
              style={{ textDecoration: 'none', color: 'inherit' }}
            >
              スコア追加
            </Link>
          </Button>
        </Box>
      )}
    </Box>
  );
};

export default UserDetailsPage;
