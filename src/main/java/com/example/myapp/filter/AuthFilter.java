package com.example.myapp.filter;

import com.example.myapp.service.UserService.JWT;
import com.example.myapp.service.UserService.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class AuthFilter extends OncePerRequestFilter {

    private final JWT jwt;

    @Autowired
    public AuthFilter(JWT jwt) {
        this.jwt = jwt;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Cookie[] cookies = request.getCookies();

        if (cookies != null){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals("token")){
                    try {
                        Claims data = jwt.decodeToken(cookie.getValue());
                        request.setAttribute("isAuth", true);
                    }
                    catch (Exception ex){
                        request.setAttribute("isAuth", false);
                    }
                }
            }
        }
        else
            request.setAttribute("isAuth", false);

        filterChain.doFilter(request, response);
    }
}
