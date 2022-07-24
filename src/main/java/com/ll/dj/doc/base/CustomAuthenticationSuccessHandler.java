package com.ll.dj.doc.base;

import com.ll.dj.doc.member.entity.Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response);

        Member member = (Member) authentication.getPrincipal();
        String name = member.getName();

        String message = URLEncoder.encode("%s님 환영합니다.".formatted(name), "UTF-8");
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            getRedirectStrategy().sendRedirect(request, response, "%s?success=true&message=%s".formatted(redirectUrl, message));
        } else {
            getRedirectStrategy().sendRedirect(request, response, "%s?success=true&message=%s".formatted(getDefaultTargetUrl(), message));
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
