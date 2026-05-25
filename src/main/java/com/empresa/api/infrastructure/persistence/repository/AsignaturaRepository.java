package com.empresa.api.infrastructure.persistence.repository;

import com.empresa.api.infrastructure.persistence.entity.AsignaturaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsignaturaRepository extends JpaRepository<AsignaturaEntity, Long> {
    List<AsignaturaEntity> findByProgramaId(Long programaId);
    boolean existsByNombreAndProgramaId(String nombre, Long programaId);
    boolean existsByNombreAndProgramaIdAndIdNot(String nombre, Long programaId, Long id);
}
