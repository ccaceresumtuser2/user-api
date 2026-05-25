package com.empresa.api.application.port.out;

import com.empresa.api.domain.model.Asignatura;

import java.util.List;
import java.util.Optional;

public interface AsignaturaPortOut {
    Asignatura crear(Asignatura asignatura);
    List<Asignatura> findAll();
    Optional<Asignatura> findById(Long id);
    List<Asignatura> findByPrograma(Long programaId);
    Asignatura actualizar(Long id, Asignatura asignatura);
    void eliminar(Long id);
    boolean existsByNombreAndProgramaId(String nombre, Long programaId);
    boolean existsByNombreAndProgramaIdAndIdNot(String nombre, Long programaId, Long id);
}
