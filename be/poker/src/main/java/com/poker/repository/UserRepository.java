package com.poker.repository;

import com.poker.domain.User;
import com.poker.dto.DBUser;
import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(Long userId);

    //新規ユーザー登録メソッド
    void insertUser(User user);

    //ユーザー情報の更新
    void updateUser(User user);

    DBUser loginUser(String username);
}
