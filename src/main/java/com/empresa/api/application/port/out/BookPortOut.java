package com.empresa.api.application.port.out;

import com.empresa.api.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookPortOut {
    Book crear(Book book);
    List<Book> findAll();
    Optional<Book> findById(Long id);
    List<Book> findByAuthor(Long authorId);
    Book actualizar(Long id, Book book);
    void eliminar(Long id);
    boolean existsByTituloAndAnioPublicacion(String titulo, Integer anioPublicacion);
    boolean existsByTituloAndAnioPublicacionAndIdNot(String titulo, Integer anioPublicacion, Long id);
}
