package com.priyansu.project.lovable_clone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() { //to connect to front-end
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5174", "http://localhost:5173") //can allow multiple front-end
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")  //Allow React App to access
                        .allowCredentials(true) //allows cookies (to send to front end)
                        .allowedHeaders("*");
            }
        };
    }
}
