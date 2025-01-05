// src/main/java/com/poker/security/LoggingAuthenticationFilter.java
package com.poker.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class LoggingAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAuthenticationFilter.class);

    public LoggingAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // usernameParameterとpasswordParameterは親クラスの設定を参照
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // ★ 危険：パスワードのログ出力 ★
        logger.warn("【DEBUG】Username={} / Password={}", username, password);

        // 親クラスの処理を呼び出して認証処理を実行
        return super.attemptAuthentication(request, response);
    }
}
