package com.poker.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        // (1) 認証済みユーザー情報を取得
        var principal = authentication.getPrincipal();
        Long userId = null;
        // (2) ロールを取得
        // 例: principal.getAuthorities() から "ROLE_USER", "ROLE_ADMIN" 等を取り出す
        String role = authentication.getAuthorities().iterator().next().getAuthority();
        if (principal instanceof CustomUser customUser) {
            userId = customUser.getUserId();
        }
        // 例: 認証成功時にJSONを返す
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("username", authentication.getName());
        result.put("userId", userId);
        // 必要に応じて、ロールやトークンを入れて返すことも
        result.put("role", role);

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        objectMapper.writeValue(response.getWriter(), result);
    }
}
