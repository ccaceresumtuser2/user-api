package com.empresa.api.infrastructure.config;

import com.empresa.api.domain.service.AsignaturaService;
import com.empresa.api.domain.service.AuthorService;
import com.empresa.api.domain.service.BookService;
import com.empresa.api.domain.service.FacultadService;
import com.empresa.api.domain.service.ProgramaService;
import com.empresa.api.domain.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class BeanConfig {

    private final AppMessages messages;

    @Bean
    UserService userService() {
        return new UserService(messages);
    }

    @Bean
    FacultadService facultadService() {
        return new FacultadService(messages);
    }

    @Bean
    ProgramaService programaService() {
        return new ProgramaService(messages);
    }

    @Bean
    AsignaturaService asignaturaService() {
        return new AsignaturaService(messages);
    }

    @Bean
    BookService bookService() {
        return new BookService(messages);
    }

    @Bean
    AuthorService authorService() {
        return new AuthorService(messages);
    }
}
