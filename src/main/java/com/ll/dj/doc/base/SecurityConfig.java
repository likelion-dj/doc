package com.ll.dj.doc.base;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.GET;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(GET, "/").permitAll()
                                .antMatchers(GET, "/articles/new").permitAll()
                                .anyRequest().authenticated()
                );

        return http.build();
    }

    /**
     * static 디렉터리의 하위 파일 목록은 무시
     * - 보안 필터를 거치지 않는다
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                        PathRequest
                                .toStaticResources()
                                .atCommonLocations())
                .antMatchers("/error", "/res/**");
    }
}
