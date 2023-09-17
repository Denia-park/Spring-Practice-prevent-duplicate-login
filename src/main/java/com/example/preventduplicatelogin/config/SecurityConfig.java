package com.example.preventduplicatelogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())

                .cors(cors -> cors.disable())

                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers(new AntPathRequestMatcher("/duplicated-login")).permitAll()
                                .anyRequest().authenticated()
                )

                .formLogin(login ->
                        login
                                .loginPage("/login") // 로그인페이지를 호출할 /login 호출
                                .loginProcessingUrl("/login-process") // form action url
                                .defaultSuccessUrl("/", true)
                                .permitAll()
                )
                .logout(Customizer.withDefaults());

        return http.build();
    }
}
