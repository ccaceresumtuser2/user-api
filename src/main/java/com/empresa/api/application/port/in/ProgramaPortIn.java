package com.empresa.api.application.port.in;

import com.empresa.api.domain.model.Programa;

import java.util.List;
import java.util.Optional;

public interface ProgramaPortIn {
    Programa crear(Programa programa);
    List<Programa> findAll();
    Optional<Programa> findById(Long id);
    List<Programa> findByFacultad(Long facultadId);
    Programa actualizar(Long id, Programa programa);
    void eliminar(Long id);
}
