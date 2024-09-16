package com.poker.service;

import com.poker.domain.User;
import com.poker.domain.UserList;
import com.poker.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserList findAll() {
        UserList userList = new UserList();
        userList.setUsers(this.userRepository.findAll());
        return userList;
    }

    @Override
    public User getUserById(Long userId){
        return userRepository.findById(userId);
    }
}
