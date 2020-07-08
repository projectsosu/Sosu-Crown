package com.sosu.rest.crown.core.config;

import com.sosu.rest.crown.core.security.SecurityCheckerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityCheckerInterceptor()).
                excludePathPatterns(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/sosu-swagger.html",
                        "/sosu-swagger"
                ).pathMatcher(new AntPathMatcher());
    }
}