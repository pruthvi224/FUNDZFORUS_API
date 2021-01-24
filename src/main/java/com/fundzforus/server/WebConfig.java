package com.fundzforus.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                "/swagger-ui.html",
                "/webjars/**",
                "/img/**",
                "/css/**")
                .addResourceLocations(
                        "classpath:/META-INF/swagger-ui.html",
                        "classpath:/META-INF/resources/webjars/",
                        "classpath:/img/",
                        "classpath:/css/");
    }
}