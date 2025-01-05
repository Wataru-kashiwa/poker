package com.poker.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // すべてのパスでCORSを許可する例
        registry.addMapping("/**")
                // フロントエンドは http://localhost:3000 から来るため
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        // 認証情報(セッション/Cookie)を使うなら以下を有効化
        // .allowCredentials(true)
        ;
    }
}
