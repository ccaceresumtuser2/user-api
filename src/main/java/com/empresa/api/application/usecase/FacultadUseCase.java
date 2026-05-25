package com.empresa.api.application.usecase;

import com.empresa.api.application.port.in.FacultadPortIn;
import com.empresa.api.application.port.out.FacultadPortOut;
import com.empresa.api.domain.model.Facultad;
import com.empresa.api.domain.service.FacultadService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class FacultadUseCase implements FacultadPortIn {

    private final FacultadService facultadService;
    private final FacultadPortOut facultadPortOut;

    @Override
    public Facultad crear(Facultad facultad) {
        log.debug("Caso de uso: crear facultad nombre={}", facultad.getNombre());
        facultadService.validar(facultad);
        facultadService.validarDuplicado(
            facultadPortOut.existsByNombre(facultad.getNombre()),
            facultad.getNombre()
        );
        return facultadPortOut.crear(facultad);
    }

    @Override
    public List<Facultad> findAll() {
        log.debug("Caso de uso: findAll facultades");
        List<Facultad> facultades = facultadPortOut.findAll();
        facultadService.validarNoVacia(facultades);
        return facultades;
    }

    @Override
    public Optional<Facultad> findById(Long id) {
        log.debug("Caso de uso: findById facultad id={}", id);
        return facultadPortOut.findById(id);
    }

    @Override
    public Facultad actualizar(Long id, Facultad facultad) {
        log.debug("Caso de uso: actualizar facultad id={}", id);
        facultadService.validar(facultad);
        facultadService.validarDuplicado(
            facultadPortOut.existsByNombreAndIdNot(facultad.getNombre(), id),
            facultad.getNombre()
        );
        return facultadPortOut.actualizar(id, facultad);
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Caso de uso: eliminar facultad id={}", id);
        facultadPortOut.eliminar(id);
    }
}
