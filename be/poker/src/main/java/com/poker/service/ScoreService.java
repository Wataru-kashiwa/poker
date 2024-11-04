package com.poker.service;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;
import java.util.List;

public interface ScoreService{

    ScoreList findAll();

    ScoreList findByUserId(Long userId);

    void insertScore(Score score);
}
