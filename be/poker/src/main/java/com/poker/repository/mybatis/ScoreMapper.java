package com.poker.repository.mybatis;

import com.poker.domain.Score;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScoreMapper {
    // ポーカースコア全件取得
    List<Score> findAll();

    // ユーザーIDで検索
    Score findByUserId(Long userId);

    // その他のメソッド（追加、更新、削除）も必要に応じて定義
}
