package com.empresa.api.application.port.out;

import com.empresa.api.domain.model.Programa;

import java.util.List;
import java.util.Optional;

public interface ProgramaPortOut {
    Programa crear(Programa programa);
    List<Programa> findAll();
    Optional<Programa> findById(Long id);
    List<Programa> findByFacultad(Long facultadId);
    Programa actualizar(Long id, Programa programa);
    void eliminar(Long id);
    boolean existsByNombreAndFacultadId(String nombre, Long facultadId);
    boolean existsByNombreAndFacultadIdAndIdNot(String nombre, Long facultadId, Long id);
}
