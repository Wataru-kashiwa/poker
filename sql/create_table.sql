-- データベースを作成（存在しない場合）
CREATE DATABASE IF NOT EXISTS poker_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- データベースを使用
USE poker_db;

-- 既存のテーブルを削除
DROP TABLE IF EXISTS poker_scores;
DROP TABLE IF EXISTS users;

-- ユーザー情報を保存するテーブル
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- ポーカーのスコアを保存するテーブル
CREATE TABLE poker_scores (
    score_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    username VARCHAR(50) NOT NULL,
    game_date DATE NOT NULL,
    score INT NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- usersテーブルにサンプルデータを挿入（パスワードをハッシュ化）
INSERT INTO users (user_id, username, email, password) VALUES
(1, '山田太郎', 'taro.yamada@example.com', SHA2('password123', 256)),
(2, '佐藤花子', 'hanako.sato@example.com', SHA2('password456', 256)),
(3, '鈴木一郎', 'ichiro.suzuki@example.com', SHA2('password789', 256));

-- poker_scoresテーブルにサンプルデータを挿入（ユーザー名とユーザーIDを記載）
INSERT INTO poker_scores (user_id, username, game_date, score) VALUES
(1, '山田太郎', '2023-10-01', 1500),
(2, '佐藤花子', '2023-10-02', 2000),
(3, '鈴木一郎', '2023-10-03', 1800),
(1, '山田太郎', '2023-10-04', 2200),
(2, '佐藤花子', '2023-10-05', 1700);
