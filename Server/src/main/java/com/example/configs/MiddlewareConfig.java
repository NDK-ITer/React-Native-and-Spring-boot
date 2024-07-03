package com.example.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.middlewares.AuthenInterceptor;
import com.example.middlewares.SignUpInterceptor;

@Configuration
public class MiddlewareConfig implements WebMvcConfigurer {
    @Autowired
    private SignUpInterceptor signUpInterceptor;
    @Autowired
    private AuthenInterceptor authenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signUpInterceptor)
                .addPathPatterns("/api/auth/sign-up");
        // registry.addInterceptor(authenInterceptor)
        //     .addPathPatterns("/api/hc/**");
    }
}
