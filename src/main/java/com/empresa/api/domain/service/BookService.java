package com.empresa.api.domain.service;

import com.empresa.api.domain.model.Book;
import com.empresa.api.infrastructure.config.AppMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private static final int ANIO_MIN = 1000;

    private final AppMessages messages;

    public BookService(AppMessages messages) {
        this.messages = messages;
    }

    public void validar(Book book) {
        log.debug("Validando reglas de negocio para libro: {}", book.getTitulo());
        List<String> errores = new ArrayList<>();

        if (book.getTitulo() == null || book.getTitulo().isBlank())
            errores.add(messages.getBookTituloObligatorio());
        if (book.getAnioPublicacion() == null)
            errores.add(messages.getBookAnioObligatorio());
        if (book.getAnioPublicacion() != null && book.getAnioPublicacion() < ANIO_MIN)
            errores.add(String.format(messages.getBookAnioMinimo(), ANIO_MIN));
        if (book.getAnioPublicacion() != null && book.getAnioPublicacion() > Year.now().getValue())
            errores.add(messages.getBookAnioMaximo());

        if (!errores.isEmpty()) {
            String mensaje = String.join(", ", errores);
            log.warn("Validacion de negocio fallida para libro: {}", mensaje);
            throw new IllegalArgumentException(mensaje);
        }
        log.debug("Libro validado correctamente: {}", book.getTitulo());
    }

    public void validarDuplicado(boolean existe, String titulo, Integer anio) {
        if (existe) {
            log.warn("Intento de insertar libro duplicado: {} ({})", titulo, anio);
            throw new IllegalArgumentException(
                String.format(messages.getBookDuplicado(), titulo, anio));
        }
    }

    public void validarNoVacia(List<Book> books) {
        if (books.isEmpty()) {
            log.warn("La consulta de libros retorno una lista vacia");
            throw new IllegalArgumentException(messages.getBookListaVacia());
        }
    }

    public void validarNoVaciaByAutor(List<Book> books, Long authorId) {
        if (books.isEmpty()) {
            log.warn("No se encontraron libros para authorId={}", authorId);
            throw new IllegalArgumentException(
                String.format(messages.getBookListaVaciaAutor(), authorId));
        }
    }
}
