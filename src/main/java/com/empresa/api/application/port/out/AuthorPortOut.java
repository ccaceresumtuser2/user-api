package com.empresa.api.application.port.out;

import com.empresa.api.domain.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorPortOut {
    Author crear(Author author);
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Author actualizar(Long id, Author author);
    void eliminar(Long id);
    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdNot(String nombre, Long id);
}
