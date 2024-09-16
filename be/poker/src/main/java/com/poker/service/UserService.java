package com.poker.service;

import com.poker.domain.User;
import com.poker.domain.UserList;

public interface UserService {
    UserList findAll();
    User getUserById(Long userId);
}
