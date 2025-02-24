package com.poker.service;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;
import com.poker.dto.ScoreEditRequest;
import java.util.List;

public interface ScoreService{

    ScoreList findAll();

    ScoreList findByUserId(Long userId);

    Score findByScoreId(Long scoreId);

    void insertScore(Score score);
    void updateScore(ScoreEditRequest scoreEditRequest);
}
