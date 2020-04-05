package com.mieo.common.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class webMvcConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/login").setViewName("login");
    }

    /**
     * 本地资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //前面为相对映射路径，第二个为本地资源绝对路径
        registry.addResourceHandler("/localImages/**").addResourceLocations("file:H:/JavaSave/idea workspace/SPMS/src/main/resources/static/images/");
    }


}
