package com.poker.repository.mybatis;

import com.poker.domain.User;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // ユーザー全件取得
    List<User> findAll();

    // ユーザーIDで検索
    User findById(Long userId);

    // その他のメソッド（追加、更新、削除）も必要に応じて定義

}
