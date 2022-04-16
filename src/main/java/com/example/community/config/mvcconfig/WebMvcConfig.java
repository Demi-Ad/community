package com.example.community.config.mvcconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * NginX 연동시 해당 코드 비활성화 필요
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final String resourceLocation;


    public WebMvcConfig(@Value("${static.path.resource-location}") String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profile/**").addResourceLocations(resourceLocation);
    }
}
