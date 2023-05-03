package com.ssafy.todaktodak.global.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedOriginPatterns("*") // 일반적으로 localhost:3000이나 http://hostname/uri~로 설정할텐데 앱으로 하게되면 어떨지 모르겠음
                .allowedMethods("*")
                .allowedHeaders("*");

    }

}
