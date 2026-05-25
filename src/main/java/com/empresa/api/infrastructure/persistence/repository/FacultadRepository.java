package com.empresa.api.infrastructure.persistence.repository;

import com.empresa.api.infrastructure.persistence.entity.FacultadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultadRepository extends JpaRepository<FacultadEntity, Long> {
    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdNot(String nombre, Long id);
}
