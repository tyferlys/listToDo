package com.example.myapp.config;

import com.example.myapp.filter.AuthFilter;
import com.example.myapp.filter.AuthSecondFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class FilterAuthConfig {

    @Bean
    public FilterRegistrationBean<AuthSecondFilter> loggingFilter(){
        FilterRegistrationBean<AuthSecondFilter> registrationBean = new FilterRegistrationBean<AuthSecondFilter>(new AuthSecondFilter());

        registrationBean.addUrlPatterns(
                "/note/*",
                "/user/*"
        );
        registrationBean.setOrder(2);

        return  registrationBean;
    }
}
