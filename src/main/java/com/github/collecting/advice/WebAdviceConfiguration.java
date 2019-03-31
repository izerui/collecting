package com.github.collecting.advice;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by serv on 2016/10/18.
 */
@Configuration
public class WebAdviceConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 此登录页仅做测试用
        registry.addViewController("/login").setViewName("login");

        registry.addRedirectViewController("/api.html", "/swagger-ui.html");
        registry.addRedirectViewController("/api", "/swagger-ui.html");
    }


}
