package com.example.community.config.mvcconfig;

import com.example.community.admin.accountManage.resolver.AccountBlockArgResolver;
import com.example.community.admin.postManage.resolver.SearchFieldResolver;
import com.example.community.domain.post.resolver.PostSearchParamArgResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * NginX 연동시 해당 코드 비활성화 필요
 */
@Configuration
public class CommonWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/image/upload").allowedOrigins("*");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new PostSearchParamArgResolver());
        resolvers.add(new AccountBlockArgResolver());
        resolvers.add(new SearchFieldResolver());
    }

}
