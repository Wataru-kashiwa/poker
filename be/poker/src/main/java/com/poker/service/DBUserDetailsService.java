package com.poker.service;


import com.poker.dto.DBUser;
import com.poker.repository.mybatis.UserMapper;
import com.poker.security.CustomUser;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.poker.repository.UserRepository;

@Service
public class DBUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    public DBUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DBからユーザー情報を取得
        DBUser dbUser =  userRepository.loginUser(username);
        System.out.println(dbUser.getUserId());
        if (dbUser == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // ここでは簡単のため、ROLE_USERを付与
        // 本来はusersテーブルや別テーブルでロールを管理することが多い
        var authorities = List.of(new SimpleGrantedAuthority(dbUser.getRole()));
        // DBのpasswordがBCryptか平文かによって、エンコード済みかどうかを判断
        // Spring Securityは{bcrypt}$2a$ のような書式があれば自動でBCryptを判定可能
        // もしくは SecurityConfig で passwordEncoder() を使用して認証
        // カスタムUserのコンストラクタに userId を渡す
        return new CustomUser(
                dbUser.getUsername(),
                dbUser.getPassword(), // BCryptyHash or ...
                authorities,
                dbUser.getUserId()
        );
    }
}
