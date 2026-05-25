package com.empresa.api.infrastructure.persistence.repository;

import com.empresa.api.infrastructure.persistence.entity.ProgramaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramaRepository extends JpaRepository<ProgramaEntity, Long> {
    List<ProgramaEntity> findByFacultadId(Long facultadId);
    boolean existsByNombreAndFacultadId(String nombre, Long facultadId);
    boolean existsByNombreAndFacultadIdAndIdNot(String nombre, Long facultadId, Long id);
}
