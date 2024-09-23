package com.poker.service;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;
import com.poker.repository.ScoreRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceImpl implements ScoreService{

    private final ScoreRepository scoreRepository;

    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public ScoreList findAll(){
        ScoreList scoreList = new ScoreList();
        scoreList.setScoreList(scoreRepository.findAll());
        return scoreList;
    }

    @Override
    public ScoreList findByUserId(Long userId) {
        ScoreList scoreList = new ScoreList();
        scoreList.setScoreList(scoreRepository.findByUserId(userId));
        return scoreList;
    }
}
