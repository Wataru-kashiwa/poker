package com.poker.service;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;

public interface ScoreService{

    ScoreList findAll();

    Score findByUserId(Long userId);
}
