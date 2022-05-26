package com.example.community.config.mvcconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Profile({"local","h2"})
public class WebMvcConfig implements WebMvcConfigurer {

    private final String profileLocation;
    private final String postImgLocation;
    @Autowired
    public WebMvcConfig(@Value("${static.profile.resource-location}") String profileLocation, @Value("${static.postImg.resource-location}") String postImgLocation) {
        this.profileLocation = profileLocation;
        this.postImgLocation = postImgLocation;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profile/**").addResourceLocations(profileLocation);
        registry.addResourceHandler("/img/**").addResourceLocations(postImgLocation);
    }

}
