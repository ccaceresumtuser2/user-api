package com.empresa.api.application.port.in;

import com.empresa.api.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorPortIn {
    Author crear(Author author);
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Author actualizar(Long id, Author author);
    void eliminar(Long id);
}
