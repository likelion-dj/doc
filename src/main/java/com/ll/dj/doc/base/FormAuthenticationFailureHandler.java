package com.ll.dj.doc.base;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class FormAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String message = "";
        if (exception instanceof UsernameNotFoundException) {
            message = "아이디를 찾을 수 없습니다.";
        }

        message = URLEncoder.encode(message, "UTF-8");

        setDefaultFailureUrl("/login?error=true&exception=".formatted(message));

        super.onAuthenticationFailure(request, response, exception);
    }
}
