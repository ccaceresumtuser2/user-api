package com.empresa.api.application.usecase;

import com.empresa.api.application.port.in.BookPortIn;
import com.empresa.api.application.port.out.BookPortOut;
import com.empresa.api.domain.model.Book;
import com.empresa.api.domain.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BookUseCase implements BookPortIn {

    private final BookService bookService;
    private final BookPortOut bookPortOut;

    @Override
    public Book crear(Book book) {
        log.debug("Caso de uso: crear libro titulo={}", book.getTitulo());
        bookService.validar(book);
        bookService.validarDuplicado(
            bookPortOut.existsByTituloAndAnioPublicacion(book.getTitulo(), book.getAnioPublicacion()),
            book.getTitulo(), book.getAnioPublicacion()
        );
        return bookPortOut.crear(book);
    }

    @Override
    public List<Book> findAll() {
        log.debug("Caso de uso: findAll libros");
        List<Book> books = bookPortOut.findAll();
        bookService.validarNoVacia(books);
        return books;
    }

    @Override
    public Optional<Book> findById(Long id) {
        log.debug("Caso de uso: findById libro id={}", id);
        return bookPortOut.findById(id);
    }

    @Override
    public List<Book> findByAuthor(Long authorId) {
        log.debug("Caso de uso: findByAuthor authorId={}", authorId);
        List<Book> books = bookPortOut.findByAuthor(authorId);
        bookService.validarNoVaciaByAutor(books, authorId);
        return books;
    }

    @Override
    public Book actualizar(Long id, Book book) {
        log.debug("Caso de uso: actualizar libro id={}", id);
        bookService.validar(book);
        bookService.validarDuplicado(
            bookPortOut.existsByTituloAndAnioPublicacionAndIdNot(book.getTitulo(), book.getAnioPublicacion(), id),
            book.getTitulo(), book.getAnioPublicacion()
        );
        return bookPortOut.actualizar(id, book);
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Caso de uso: eliminar libro id={}", id);
        bookPortOut.eliminar(id);
    }
}
