package com.poker.repository.mybatis;

import com.poker.domain.User;
import com.poker.dto.DBUser;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    // ユーザー全件取得
    List<User> findAll();

    // ユーザーIDで検索
    User findById(Long userId);

    // その他のメソッド（追加、更新、削除）も必要に応じて定義
    // 新規ユーザー登録メソッド
    void insertUser(User user);

    // ユーザー情報の更新
    void updateUser(User user);

    // ユーザーログイン情報を取得する
    DBUser loginUser(@Param("username") String username);
}
