package com.empresa.api.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("API de Usuarios")
                .version("1.0.0")
                .description("API REST para la gestión de usuarios de la empresa")
                .contact(new Contact()
                    .name("Carlos Jose Caceres Ochoa")
                    .email("car.caceres.ochoa@gmail.com"))
                .license(new License()
                    .name("MIT")));
    }
}
