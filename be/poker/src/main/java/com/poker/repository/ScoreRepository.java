package com.poker.repository;

import com.poker.domain.Score;
import java.util.List;

public interface ScoreRepository {
    // ポーカースコア全件取得
    List<Score> findAll();

    // ユーザーIDで検索
    Score findByUserId(Long userId);
}
