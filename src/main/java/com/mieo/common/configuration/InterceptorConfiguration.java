package com.mieo.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration

public class InterceptorConfiguration implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(myInterceptor).addPathPatterns("/**");
        List<String> urls=new ArrayList<>();
        urls.add("/login");
        urls.add("/index");
//        registry.addInterceptor(loginInterceptor).excludePathPatterns(urls);
    }
}
