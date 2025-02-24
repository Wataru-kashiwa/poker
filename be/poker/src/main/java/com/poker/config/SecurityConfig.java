// src/main/java/com/poker/config/SecurityConfig.java
package com.poker.config;

import com.poker.security.CustomAuthFailureHandler;
import com.poker.security.CustomAuthSuccessHandler;
import com.poker.service.DBUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final DBUserDetailsService userDetailsService;

    public SecurityConfig(DBUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // (1) BCryptエンコーダ
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    // (2) DaoAuthenticationProvider
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // DBからユーザーを読み込むサービス
        provider.setUserDetailsService(userDetailsService);
        // パスワード照合にBCryptを使う
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // (3) AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // (4) SecurityFilterChain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/scores/edit", "/scores/delete").permitAll()
                        .requestMatchers("/scores/add").permitAll()
                        .requestMatchers("/scores/**", "/home").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(new CustomAuthSuccessHandler())
                        .failureHandler(new CustomAuthFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll()
                );

        // (5) DaoAuthenticationProviderを手動で適用する場合
        // http.authenticationProvider(authProvider());

        return http.build();
    }

}
