package com.slonik.rest_ajax.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {


        boolean isUser = false;
        boolean isAdmin = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority :
                authorities) {
            if (authority.getAuthority().equals("ROLE_USER")) {
                isUser = true;
            }
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                isAdmin = true;
            }
        }
        if (isAdmin) {
            httpServletResponse.sendRedirect("/");
        } else if (isUser) {
            httpServletResponse.sendRedirect("/");
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}
