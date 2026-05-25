package com.empresa.api.application.port.in;

import com.empresa.api.domain.model.Asignatura;

import java.util.List;
import java.util.Optional;

public interface AsignaturaPortIn {
    Asignatura crear(Asignatura asignatura);
    List<Asignatura> findAll();
    Optional<Asignatura> findById(Long id);
    List<Asignatura> findByPrograma(Long programaId);
    Asignatura actualizar(Long id, Asignatura asignatura);
    void eliminar(Long id);
}
