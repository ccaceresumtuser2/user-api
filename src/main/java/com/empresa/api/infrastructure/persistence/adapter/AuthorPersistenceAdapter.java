package com.empresa.api.infrastructure.persistence.adapter;

import com.empresa.api.application.port.out.AuthorPortOut;
import com.empresa.api.domain.model.Author;
import com.empresa.api.infrastructure.config.AppMessages;
import com.empresa.api.infrastructure.persistence.entity.AuthorEntity;
import com.empresa.api.infrastructure.persistence.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class AuthorPersistenceAdapter implements AuthorPortOut {

    private final AuthorRepository authorRepository;
    private final AppMessages messages;

    @Override
    public Author crear(Author author) {
        log.debug("Persistiendo autor: {}", author.getNombre());
        AuthorEntity saved = authorRepository.save(toEntity(author));
        log.info("Autor persistido con id={}", saved.getId());
        return toDomain(saved);
    }

    @Override
    public List<Author> findAll() {
        log.debug("Consultando todos los autores");
        List<Author> result = authorRepository.findAll().stream().map(this::toDomain).toList();
        log.info("findAll - {} autores encontrados", result.size());
        return result;
    }

    @Override
    public Optional<Author> findById(Long id) {
        log.debug("Consultando autor por id={}", id);
        Optional<Author> result = authorRepository.findById(id).map(this::toDomain);
        result.ifPresentOrElse(
            a -> log.info("findById - Autor encontrado id={}", id),
            () -> log.warn("findById - No existe autor con id={}", id)
        );
        return result;
    }

    @Override
    public Author actualizar(Long id, Author author) {
        log.debug("Actualizando autor id={}", id);
        AuthorEntity entity = authorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                String.format(messages.getAuthorNoEncontrado(), id)));
        entity.setNombre(author.getNombre());
        entity.setNacionalidad(author.getNacionalidad());
        Author result = toDomain(authorRepository.save(entity));
        log.info("Autor actualizado id={}", id);
        return result;
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Eliminando autor id={}", id);
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException(String.format(messages.getAuthorNoEncontrado(), id));
        }
        authorRepository.deleteById(id);
        log.info("Autor eliminado id={}", id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return authorRepository.existsByNombre(nombre);
    }

    @Override
    public boolean existsByNombreAndIdNot(String nombre, Long id) {
        return authorRepository.existsByNombreAndIdNot(nombre, id);
    }

    private AuthorEntity toEntity(Author author) {
        return AuthorEntity.builder()
            .nombre(author.getNombre())
            .nacionalidad(author.getNacionalidad())
            .build();
    }

    private Author toDomain(AuthorEntity entity) {
        return Author.builder()
            .id(entity.getId())
            .nombre(entity.getNombre())
            .nacionalidad(entity.getNacionalidad())
            .build();
    }
}
