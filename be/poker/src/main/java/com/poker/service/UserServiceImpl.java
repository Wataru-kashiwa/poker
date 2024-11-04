package com.poker.service;

import com.poker.domain.User;
import com.poker.domain.UserList;
import com.poker.dto.UserEditRequest;
import com.poker.dto.UserRequest;
import com.poker.repository.UserRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    @Override
    @Transactional
    public void updateUser(UserEditRequest userEditRequest) {
        // ユーザーの存在確認
        User existingUser = userRepository.findById(userEditRequest.getUserId());
        if (existingUser == null) {
            logger.warn("User not found: userId={}", userEditRequest.getUserId());
            throw new IllegalArgumentException("User not found");
        }

        // 更新するフィールドを設定
        existingUser.setUserId(userEditRequest.getUserId());
        existingUser.setUserName(userEditRequest.getUsername());
        existingUser.setEmail(userEditRequest.getEmail());
        // #TODO updatedByをログインユーザーから取得する
        existingUser.setUpdatedBy(userEditRequest.getUpdatedBy());

        // ログ出力（ユーザー名など）
        logger.info("Updating user: userId={}, username={}, updatedBy={}",
                existingUser.getUserId(),
                existingUser.getUserName(),
                existingUser.getUpdatedBy());

        if (userEditRequest.getPassword() != null && !userEditRequest.getPassword().isEmpty()) {
            // パスワードのハッシュ化
            String hashedPassword = passwordEncoder.encode(userEditRequest.getPassword());
            existingUser.setPassword(hashedPassword);

            // パスワード更新のログ（パスワード自体は出力しない）
            logger.info("Password updated for userId={}", existingUser.getUserId());
        }

        userRepository.updateUser(existingUser);
    }

}
