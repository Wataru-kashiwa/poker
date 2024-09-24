package com.poker.repository;

import com.poker.domain.User;
import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(Long userId);

    //新規ユーザー登録メソッド
    void insertUser(User user);
}
