package com.poker.repository;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;
import java.util.List;

public interface ScoreRepository {
    // ポーカースコア全件取得
    List<Score> findAll();

    // ユーザーIDで検索
    List<Score> findByUserId(Long userId);
    Score findById(Long scoreId);

    Score findByScoreId(Long scoreId);

    void insertScore(Score score);
    void updateScore(Score score);
}
