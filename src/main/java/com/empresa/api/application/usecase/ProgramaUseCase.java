package com.empresa.api.application.usecase;

import com.empresa.api.application.port.in.ProgramaPortIn;
import com.empresa.api.application.port.out.ProgramaPortOut;
import com.empresa.api.domain.model.Programa;
import com.empresa.api.domain.service.ProgramaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProgramaUseCase implements ProgramaPortIn {

    private final ProgramaService programaService;
    private final ProgramaPortOut programaPortOut;

    @Override
    public Programa crear(Programa programa) {
        log.debug("Caso de uso: crear programa nombre={}", programa.getNombre());
        programaService.validar(programa);
        programaService.validarDuplicado(
            programaPortOut.existsByNombreAndFacultadId(programa.getNombre(), programa.getFacultadId()),
            programa.getNombre(),
            programa.getFacultadId()
        );
        return programaPortOut.crear(programa);
    }

    @Override
    public List<Programa> findAll() {
        log.debug("Caso de uso: findAll programas");
        return programaPortOut.findAll();
    }

    @Override
    public Optional<Programa> findById(Long id) {
        log.debug("Caso de uso: findById programa id={}", id);
        return programaPortOut.findById(id);
    }

    @Override
    public List<Programa> findByFacultad(Long facultadId) {
        log.debug("Caso de uso: findByFacultad facultadId={}", facultadId);
        List<Programa> programas = programaPortOut.findByFacultad(facultadId);
        programaService.validarNoVacia(programas, facultadId);
        return programas;
    }

    @Override
    public Programa actualizar(Long id, Programa programa) {
        log.debug("Caso de uso: actualizar programa id={}", id);
        programaService.validar(programa);
        programaService.validarDuplicado(
            programaPortOut.existsByNombreAndFacultadIdAndIdNot(programa.getNombre(), programa.getFacultadId(), id),
            programa.getNombre(),
            programa.getFacultadId()
        );
        return programaPortOut.actualizar(id, programa);
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Caso de uso: eliminar programa id={}", id);
        programaPortOut.eliminar(id);
    }
}
