package com.poker.repository;

import com.poker.domain.Score;
import com.poker.domain.ScoreList;
import com.poker.repository.mybatis.ScoreMapper;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreRepositoryImpl implements ScoreRepository{
    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final SqlSession sqlSession;

    public ScoreRepositoryImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<Score> findAll() {
        return this.sqlSession.getMapper(ScoreMapper.class).findAll();
    }

    @Override
    public List<Score> findByUserId(Long userId) {
        return this.sqlSession.getMapper(ScoreMapper.class).findByUserId(userId);
    }

    @Override
    public Score findById(Long scoreId) {
        return this.sqlSession.getMapper(ScoreMapper.class).findById(scoreId);
    }

    @Override
    public void insertScore(Score score) {
        this.sqlSession.getMapper(ScoreMapper.class).insertScore(score);
    }

    @Override
    public void updateScore(Score score) {
        this.sqlSession.getMapper(ScoreMapper.class).updateScore(score);
    }

}
