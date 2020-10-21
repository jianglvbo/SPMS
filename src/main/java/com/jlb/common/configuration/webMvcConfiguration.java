package com.jlb.common.configuration;

import com.jlb.common.util.SettingUtil;
import com.jlb.model.Setting;
import com.jlb.service.Impl.SettingServiceImpl;
import com.jlb.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;


@Configuration
public class webMvcConfiguration implements WebMvcConfigurer {
    @Autowired
    SettingUtil settingUtil;
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
        Map<String, Object> setting = settingUtil.getSetting();
        String iconLocation="file:";
        iconLocation += (String) setting.get("iconLocation");
        registry.addResourceHandler("/localImages/**").addResourceLocations(iconLocation);
    }


}
