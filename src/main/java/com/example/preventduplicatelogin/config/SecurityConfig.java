package com.example.preventduplicatelogin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
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
                                .permitAll() //Login 관련 페이지에 접근할때는 Authenticate 하지 않도록 하는 설정
                )

                .logout(logout ->
                        logout
                                .logoutUrl("/logout")  /* 로그아웃 url*/
                                .logoutSuccessUrl("/login")  /* 로그아웃 성공시 이동할 url */
                                .invalidateHttpSession(true)  /*로그아웃시 세션 제거*/
                                .deleteCookies("JSESSIONID")  /*쿠키 제거*/
                                .clearAuthentication(true)    /*권한정보 제거*/
                                .permitAll())

                .sessionManagement(sm ->
                        sm
                                .maximumSessions(1)

                                //maxSessionPreventsLogin : true 일 경우, 기존에 동일한 사용자가 로그인이 되어있으면 이후 로그인이 불가
                                // false 일경우는 로그인이 되고 기존 접속된 사용자는 Session이 종료된다. false 가 기본값
                                .maxSessionsPreventsLogin(false)

                                .expiredUrl("/duplicated-login")
                                .sessionRegistry(sessionRegistry())
                );

        return http.build();
    }

    // logout 후 login할 때 정상동작을 위함
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
