'use client';

import React, { useState } from 'react';
import { useRouter } from 'next/navigation';

export default function LoginPage() {
  const router = useRouter();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  // エラーメッセージ表示用
  const [errorMsg, setErrorMsg] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    // エラーメッセージを一旦クリア
    setErrorMsg('');

    try {
      // URLエンコードされたデータを作成
      // ex) "username=新規ユーザー&password=newpassword"
      const formData = new URLSearchParams();
      formData.append('username', username);
      formData.append('password', password);

      const res = await fetch('http://localhost:8080/login', {
        // 上記の cURL と同じエンドポイントとメソッド
        method: 'POST',
        headers: { 
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        // JSONではなくURLエンコードデータをbodyにセット
        body: formData.toString(),
        // Cookie を使う場合は:
        // credentials: 'include',
      });

      if (!res.ok) {
        // 認証失敗の場合、ステータスコードなどをチェックしてエラーメッセージを設定
        setErrorMsg('ログインに失敗しました。ユーザー名またはパスワードが違います。');
        return;
      }

      // サーバーがJSONを返す前提ならパース
      const data = await res.json();
      // data: { token: string; role: string; ... } の形を想定

      // ローカルストレージに保存する例（本番では httpOnly Cookie 等を推奨）
      localStorage.setItem('auth_token', data.token);
      localStorage.setItem('user_role', data.role);
      console.log(data)

      // ログイン後のページへ遷移
      router.push('/');
    } catch (error) {
      console.error('Error logging in:', error);
      setErrorMsg('ログイン時にエラーが発生しました。');
    }
  };

  return (
    <div style={{ padding: '1rem' }}>
      <h1>ログイン</h1>
      <form onSubmit={handleSubmit} style={{ marginTop: '1rem' }}>
        {errorMsg && (
          <p style={{ color: 'red' }}>
            {errorMsg}
          </p>
        )}
        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="username" style={{ display: 'block' }}>
            ユーザー名
          </label>
          <input
            id="username"
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
            style={{ padding: '0.5rem', width: '200px' }}
          />
        </div>
        <div style={{ marginBottom: '1rem' }}>
          <label htmlFor="password" style={{ display: 'block' }}>
            パスワード
          </label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            style={{ padding: '0.5rem', width: '200px' }}
          />
        </div>
        <button type="submit" style={{ padding: '0.5rem 1rem' }}>
          ログイン
        </button>
      </form>
    </div>
  );
}
