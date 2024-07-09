package com.example.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.middlewares.AuthenInterceptor;
import com.example.middlewares.SignUpInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SignUpInterceptor signUpInterceptor;
    @Autowired
    private AuthenInterceptor authenInterceptor;

    // #region CORS Config
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3007/")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
    // #endregion

    // #region Middleware Config
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // registry.addInterceptor(signUpInterceptor)
        // .addPathPatterns("/api/authenticate/sign-up");
        registry.addInterceptor(authenInterceptor)
                .addPathPatterns("/api/hc/**");
        registry.addInterceptor(authenInterceptor)
                .addPathPatterns("/api/user/**");
    }
    // #endregion

    // #region Static File Config
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/public/**")
                .addResourceLocations("classpath:/public/")
                .setCachePeriod(3600);
    }
    // #endregion
}
