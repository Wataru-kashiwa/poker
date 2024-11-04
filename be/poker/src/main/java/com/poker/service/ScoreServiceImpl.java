package com.poker.service;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;
import com.poker.domain.User;
import com.poker.repository.ScoreRepository;
import com.poker.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScoreServiceImpl implements ScoreService{

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;

    public ScoreServiceImpl(ScoreRepository scoreRepository, UserRepository userRepository) {
        this.scoreRepository = scoreRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ScoreList findAll(){
        ScoreList scoreList = new ScoreList();
        scoreList.setScoreList(scoreRepository.findAll());
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
}
