package com.empresa.api.application.port.in;

import com.empresa.api.domain.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookPortIn {
    Book crear(Book book);
    List<Book> findAll();
    Optional<Book> findById(Long id);
    List<Book> findByAuthor(Long authorId);
    Book actualizar(Long id, Book book);
    void eliminar(Long id);
}
