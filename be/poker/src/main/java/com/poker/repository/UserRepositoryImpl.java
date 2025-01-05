package com.poker.repository;

import com.poker.domain.User;
import com.poker.dto.DBUser;
import com.poker.repository.mybatis.UserMapper;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository{

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    private final SqlSession sqlSession;

    public UserRepositoryImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<User> findAll() {
        return this.sqlSession.getMapper(UserMapper.class).findAll();
    }

    @Override
    public User findById(Long userId) {
        return this.sqlSession.getMapper(UserMapper.class).findById(userId);
    }

    @Override
    public void insertUser(User user) {
        this.sqlSession.getMapper(UserMapper.class).insertUser(user);
    }

    @Override
    public void updateUser(User user) {
        this.sqlSession.getMapper(UserMapper.class).updateUser(user);
    }

    @Override
    public DBUser loginUser(String username) { return this.sqlSession.getMapper(UserMapper.class).loginUser(username);}

}
