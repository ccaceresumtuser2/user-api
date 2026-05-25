package com.empresa.api.application.usecase;

import com.empresa.api.application.port.in.AsignaturaPortIn;
import com.empresa.api.application.port.out.AsignaturaPortOut;
import com.empresa.api.domain.model.Asignatura;
import com.empresa.api.domain.service.AsignaturaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AsignaturaUseCase implements AsignaturaPortIn {

    private final AsignaturaService asignaturaService;
    private final AsignaturaPortOut asignaturaPortOut;

    @Override
    public Asignatura crear(Asignatura asignatura) {
        log.debug("Caso de uso: crear asignatura nombre={}", asignatura.getNombre());
        asignaturaService.validar(asignatura);
        asignaturaService.validarDuplicado(
            asignaturaPortOut.existsByNombreAndProgramaId(asignatura.getNombre(), asignatura.getProgramaId()),
            asignatura.getNombre(),
            asignatura.getProgramaId()
        );
        return asignaturaPortOut.crear(asignatura);
    }

    @Override
    public List<Asignatura> findAll() {
        log.debug("Caso de uso: findAll asignaturas");
        return asignaturaPortOut.findAll();
    }

    @Override
    public Optional<Asignatura> findById(Long id) {
        log.debug("Caso de uso: findById asignatura id={}", id);
        return asignaturaPortOut.findById(id);
    }

    @Override
    public List<Asignatura> findByPrograma(Long programaId) {
        log.debug("Caso de uso: findByPrograma programaId={}", programaId);
        List<Asignatura> asignaturas = asignaturaPortOut.findByPrograma(programaId);
        asignaturaService.validarNoVacia(asignaturas, programaId);
        return asignaturas;
    }

    @Override
    public Asignatura actualizar(Long id, Asignatura asignatura) {
        log.debug("Caso de uso: actualizar asignatura id={}", id);
        asignaturaService.validar(asignatura);
        asignaturaService.validarDuplicado(
            asignaturaPortOut.existsByNombreAndProgramaIdAndIdNot(asignatura.getNombre(), asignatura.getProgramaId(), id),
            asignatura.getNombre(),
            asignatura.getProgramaId()
        );
        return asignaturaPortOut.actualizar(id, asignatura);
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Caso de uso: eliminar asignatura id={}", id);
        asignaturaPortOut.eliminar(id);
    }
}
