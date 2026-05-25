package com.empresa.api.application.port.in;

import com.empresa.api.domain.model.Facultad;

import java.util.List;
import java.util.Optional;

public interface FacultadPortIn {
    Facultad crear(Facultad facultad);
    List<Facultad> findAll();
    Optional<Facultad> findById(Long id);
    Facultad actualizar(Long id, Facultad facultad);
    void eliminar(Long id);
}
