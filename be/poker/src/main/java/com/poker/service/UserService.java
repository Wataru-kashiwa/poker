package com.poker.service;

import com.poker.domain.User;
import com.poker.domain.UserList;
import com.poker.dto.UserEditRequest;

public interface UserService {
    UserList findAll();
    User getUserById(Long userId);

    //新規ユーザー登録メソッド
    void insertUser(User user);

    // ユーザーの編集
    void updateUser(UserEditRequest userEditRequest);

}
