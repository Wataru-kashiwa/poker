package com.poker.service;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;
import com.poker.domain.User;
import com.poker.dto.ScoreEditRequest;
import com.poker.repository.ScoreRepository;
import com.poker.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class ScoreServiceImpl implements ScoreService{
    private static final Logger logger = LoggerFactory.getLogger(ScoreServiceImpl.class);

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
