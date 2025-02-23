package com.poker.service;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;
import com.poker.domain.User;
import com.poker.dto.ScoreEditRequest;
import com.poker.repository.ScoreRepository;
import com.poker.repository.UserRepository;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ScoreServiceImpl implements ScoreService{
    private static final Logger logger = LoggerFactory.getLogger(ScoreServiceImpl.class);
    // フォーマットを指定
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    public ScoreServiceImpl(ScoreRepository scoreRepository, UserRepository userRepository) {
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ScoreList findAll() {
        // 1. まず全スコアを取得
        List<Score> allScores = scoreRepository.findAll();

        // 2. userId -> 最新スコア を保持するためのマップ
        Map<Long, Score> latestByUser = new HashMap<>();

        for (Score score : allScores) {
            Long userId = score.getUserId();
            Score existing = latestByUser.get(userId);

            if (existing == null) {
                // 初めて見る userIdなら、そのまま登録
                latestByUser.put(userId, score);
            } else {
                // 既に userIdに対応するスコアがある場合、updated_atを比較して新しい方を保持
                if (score.getUpdatedAt().isAfter(existing.getUpdatedAt())) {
                    latestByUser.put(userId, score);
                }
            }
        }


        // 3. Map の value を取り出せば、userId重複なし & 最新スコアだけになっている
        List<Score> deduplicatedScores = new ArrayList<>(latestByUser.values());

        // 4. ScoreList を作って戻す
        ScoreList scoreList = new ScoreList();
        scoreList.setScoreList(deduplicatedScores);

        // デバッグ出力例
        for (Score s : scoreList.getScoreList()) {
            System.out.println("userId=" + s.getUserId() + ", updated_at=" + s.getUpdatedAt());
        }

        return scoreList;
    }


    @Override
    @Transactional
    public ScoreList findByUserId(Long userId) {
        ScoreList scoreList = new ScoreList();
        scoreList.setScoreList(scoreRepository.findByUserId(userId));
        return scoreList;
    }

    @Override
    @Transactional
    public void insertScore(Score score) {
        // ユーザーの存在チェックとusernameの取得
        User user = userRepository.findById(score.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        scoreRepository.insertScore(score);
    }

    @Override
    @Transactional
    public void updateScore(ScoreEditRequest scoreEditRequest) {
        // スコアの存在確認
        Score existingScore = scoreRepository.findById(scoreEditRequest.getScoreId());
        existingScore.setScoreId(scoreEditRequest.getScoreId());
        if (existingScore == null) {
            throw new IllegalArgumentException("Score not found");
        }
        logger.info("Updating score with id={}, new score={}, updatedBy={},scoreEditRequest scoreId{}",
                existingScore.getScoreId(),
                scoreEditRequest.getScore(),
                scoreEditRequest.getUpdatedBy(),
                scoreEditRequest.getScoreId());


        // 更新するフィールドを設定
        existingScore.setScore(scoreEditRequest.getScore());
        existingScore.setGameDate(scoreEditRequest.getGameDate());
        existingScore.setUpdatedBy(scoreEditRequest.getUpdatedBy());
        existingScore.setUpdatedAt(java.time.LocalDateTime.now());

        scoreRepository.updateScore(existingScore);
    }
}
