package com.empresa.api.application.usecase;

import com.empresa.api.application.port.in.AuthorPortIn;
import com.empresa.api.application.port.out.AuthorPortOut;
import com.empresa.api.domain.model.Author;
import com.empresa.api.domain.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthorUseCase implements AuthorPortIn {

    private final AuthorService authorService;
    private final AuthorPortOut authorPortOut;

    @Override
    public Author crear(Author author) {
        log.debug("Caso de uso: crear autor nombre={}", author.getNombre());
        authorService.validar(author);
        authorService.validarDuplicado(
            authorPortOut.existsByNombre(author.getNombre()),
            author.getNombre()
        );
        return authorPortOut.crear(author);
    }

    @Override
    public List<Author> findAll() {
        log.debug("Caso de uso: findAll autores");
        List<Author> authors = authorPortOut.findAll();
        authorService.validarNoVacia(authors);
        return authors;
    }

    @Override
    public Optional<Author> findById(Long id) {
        log.debug("Caso de uso: findById autor id={}", id);
        return authorPortOut.findById(id);
    }

    @Override
    public Author actualizar(Long id, Author author) {
        log.debug("Caso de uso: actualizar autor id={}", id);
        authorService.validar(author);
        authorService.validarDuplicado(
            authorPortOut.existsByNombreAndIdNot(author.getNombre(), id),
            author.getNombre()
        );
        return authorPortOut.actualizar(id, author);
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Caso de uso: eliminar autor id={}", id);
        authorPortOut.eliminar(id);
    }
}
