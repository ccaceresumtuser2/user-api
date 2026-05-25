package com.empresa.api.infrastructure.persistence.repository;

import com.empresa.api.infrastructure.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    boolean existsByTituloAndAnioPublicacion(String titulo, Integer anioPublicacion);
    boolean existsByTituloAndAnioPublicacionAndIdNot(String titulo, Integer anioPublicacion, Long id);
    List<BookEntity> findByAuthorsId(Long authorId);
}
