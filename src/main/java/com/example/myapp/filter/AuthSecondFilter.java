package com.example.myapp.filter;

import com.example.myapp.controller.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Order(2)
public class AuthSecondFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        Object attr = request.getAttribute("isAuth");

        if (attr != null){
            String isAuth = attr.toString();
            System.out.println(isAuth);

            if (isAuth.equals("false")){
                response.setStatus(400);

                Response errorResponseObject = new Response(400, "Invalid token");
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonError = objectMapper.writeValueAsString(errorResponseObject);

                response.getWriter().write(jsonError);
                return;
            }
        }
        else{
            Response errorResponseObject = new Response(400, "Invalid token");
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonError = objectMapper.writeValueAsString(errorResponseObject);

            response.getWriter().write(jsonError);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
