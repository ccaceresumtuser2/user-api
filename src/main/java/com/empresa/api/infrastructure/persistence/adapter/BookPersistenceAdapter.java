package com.empresa.api.infrastructure.persistence.adapter;

import com.empresa.api.application.port.out.BookPortOut;
import com.empresa.api.domain.model.Book;
import com.empresa.api.infrastructure.config.AppMessages;
import com.empresa.api.infrastructure.persistence.entity.AuthorEntity;
import com.empresa.api.infrastructure.persistence.entity.BookEntity;
import com.empresa.api.infrastructure.persistence.repository.AuthorRepository;
import com.empresa.api.infrastructure.persistence.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class BookPersistenceAdapter implements BookPortOut {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final AppMessages messages;

    @Override
    public Book crear(Book book) {
        log.debug("Persistiendo libro: {}", book.getTitulo());
        List<AuthorEntity> authors = resolverAuthors(book.getAuthorIds());
        BookEntity entity = BookEntity.builder()
            .titulo(book.getTitulo())
            .anioPublicacion(book.getAnioPublicacion())
            .authors(authors)
            .build();
        BookEntity saved = bookRepository.save(entity);
        log.info("Libro persistido con id={}", saved.getId());
        return toDomain(saved);
    }

    @Override
    public List<Book> findAll() {
        log.debug("Consultando todos los libros");
        List<Book> result = bookRepository.findAll().stream().map(this::toDomain).toList();
        log.info("findAll - {} libros encontrados", result.size());
        return result;
    }

    @Override
    public Optional<Book> findById(Long id) {
        log.debug("Consultando libro por id={}", id);
        Optional<Book> result = bookRepository.findById(id).map(this::toDomain);
        result.ifPresentOrElse(
            b -> log.info("findById - Libro encontrado id={}", id),
            () -> log.warn("findById - No existe libro con id={}", id)
        );
        return result;
    }

    @Override
    public List<Book> findByAuthor(Long authorId) {
        log.debug("Consultando libros por authorId={}", authorId);
        List<Book> result = bookRepository.findByAuthorsId(authorId).stream().map(this::toDomain).toList();
        log.info("findByAuthor - {} libros para authorId={}", result.size(), authorId);
        return result;
    }

    @Override
    public Book actualizar(Long id, Book book) {
        log.debug("Actualizando libro id={}", id);
        BookEntity entity = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                String.format(messages.getBookNoEncontrado(), id)));
        List<AuthorEntity> authors = resolverAuthors(book.getAuthorIds());
        entity.setTitulo(book.getTitulo());
        entity.setAnioPublicacion(book.getAnioPublicacion());
        entity.setAuthors(authors);
        Book result = toDomain(bookRepository.save(entity));
        log.info("Libro actualizado id={}", id);
        return result;
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Eliminando libro id={}", id);
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException(String.format(messages.getBookNoEncontrado(), id));
        }
        bookRepository.deleteById(id);
        log.info("Libro eliminado id={}", id);
    }

    @Override
    public boolean existsByTituloAndAnioPublicacion(String titulo, Integer anioPublicacion) {
        return bookRepository.existsByTituloAndAnioPublicacion(titulo, anioPublicacion);
    }

    @Override
    public boolean existsByTituloAndAnioPublicacionAndIdNot(String titulo, Integer anioPublicacion, Long id) {
        return bookRepository.existsByTituloAndAnioPublicacionAndIdNot(titulo, anioPublicacion, id);
    }

    private List<AuthorEntity> resolverAuthors(List<Long> authorIds) {
        if (authorIds == null || authorIds.isEmpty()) return new ArrayList<>();
        return authorIds.stream()
            .map(authorId -> authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException(
                    String.format(messages.getBookAutorNoEncontrado(), authorId))))
            .toList();
    }

    private Book toDomain(BookEntity entity) {
        List<Long> authorIds = entity.getAuthors().stream().map(AuthorEntity::getId).toList();
        List<String> authorNombres = entity.getAuthors().stream().map(AuthorEntity::getNombre).toList();
        return Book.builder()
            .id(entity.getId())
            .titulo(entity.getTitulo())
            .anioPublicacion(entity.getAnioPublicacion())
            .authorIds(authorIds)
            .authorNombres(authorNombres)
            .build();
    }
}
