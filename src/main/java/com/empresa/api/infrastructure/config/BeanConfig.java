package com.empresa.api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.empresa.api.domain.service.UserService;

@Configuration
public class BeanConfig {

    @Bean
    UserService userService() {
        return new UserService();
    }    
}
