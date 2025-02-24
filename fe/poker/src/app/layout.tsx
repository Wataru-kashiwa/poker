'use client'; // ここでクライアントコンポーネント化

import React, { useEffect, useState } from 'react';
import MaterialThemeProvider from './theme';
import { AppBar, Toolbar, Typography, Button, Box, CssBaseline } from '@mui/material';
import Link from 'next/link';
import { useRouter } from 'next/navigation';

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const router = useRouter();
  
  // ログイン状態を保持するステート
  const [loggedIn, setLoggedIn] = useState(false);
  const [userId, setUserId] = useState<string | null>(null);
  const [username, setUsername] = useState<string | null>(null);

  // ページマウント時に localStorage を確認してログイン情報を取得
  useEffect(() => {
    const storedUserId = localStorage.getItem('user_id');
    const storedUsername = localStorage.getItem('username');
    if (storedUserId) {
      setUserId(storedUserId);
      setUsername(storedUsername);
      setLoggedIn(true);
    }
  }, []);

  // ログアウトボタンが押された場合
  const handleLogout = () => {
    // localStorageからログイン情報を消去
    localStorage.removeItem('user_id');
    localStorage.removeItem('username');
    localStorage.removeItem('role');     // 必要に応じて他の情報も削除
    // ログイン状態をリセット
    setUserId(null);
    setUsername(null);
    setLoggedIn(false);
    // ログアウト後、トップページなどへ遷移
    router.push('/');
  };

  return (
    <html lang="ja">
      <head>
        <title>My Next App</title>
      </head>
      <body>
        <MaterialThemeProvider>
          <CssBaseline />
          
          {/* ヘッダー */}
          <AppBar position="static" sx={{ backgroundColor: '#4285F4' }}>
            <Toolbar>
              {/* 左側タイトル */}
              <Typography variant="h6" sx={{ flexGrow: 1 }}>
                Poker Scores
              </Typography>

              {/* 右側ナビゲーション */}
              <Box display="flex" alignItems="center">
                {/* ログイン状態に応じてユーザー名やIDを表示 */}
                {loggedIn && (
                  <Typography variant="body1" sx={{ mr: 2 }}>
                    ログイン中: {username} (ID: {userId})
                  </Typography>
                )}

                {/* ホーム */}
                <Button color="inherit">
                  <Link 
                    href="/" 
                    style={{ textDecoration: 'none', color: 'inherit' }}
                  >
                    ホーム
                  </Link>
                </Button>

                {/* ログインしている場合 -> ログアウトボタン表示
                    ログインしていない場合 -> ログインボタン表示 */}
                {loggedIn ? (
                  <Button color="inherit" onClick={handleLogout}>
                    ログアウト
                  </Button>
                ) : (
                  <Button color="inherit">
                    <Link
                      href="/login"
                      style={{ textDecoration: 'none', color: 'inherit' }}
                    >
                      ログイン
                    </Link>
                  </Button>
                )}

                {/* スコア追加など、ログイン状態でも表示してOKならここに配置 */}
                <Button color="inherit">
                  <Link
                    href="/scores/add"
                    style={{ textDecoration: 'none', color: 'inherit' }}
                  >
                    スコア追加
                  </Link>
                </Button>
              </Box>
            </Toolbar>
          </AppBar>

          {/* ページ本体 */}
          {children}
        </MaterialThemeProvider>
      </body>
    </html>
  );
}
