package com.poker.repository.mybatis;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScoreMapper {
    // ポーカースコア全件取得
    List<Score> findAll();

    // ユーザーIDで検索
    List<Score> findByUserId(@Param("userId") Long userId);

    // その他のメソッド（追加、更新、削除）も必要に応じて定義
    void insertScore(Score score);
}
