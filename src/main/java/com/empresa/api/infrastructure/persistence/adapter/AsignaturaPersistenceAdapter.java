package com.empresa.api.infrastructure.persistence.adapter;

import com.empresa.api.application.port.out.AsignaturaPortOut;
import com.empresa.api.domain.model.Asignatura;
import com.empresa.api.infrastructure.config.AppMessages;
import com.empresa.api.infrastructure.persistence.entity.AsignaturaEntity;
import com.empresa.api.infrastructure.persistence.entity.ProgramaEntity;
import com.empresa.api.infrastructure.persistence.repository.AsignaturaRepository;
import com.empresa.api.infrastructure.persistence.repository.ProgramaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class AsignaturaPersistenceAdapter implements AsignaturaPortOut {

    private final AsignaturaRepository asignaturaRepository;
    private final ProgramaRepository programaRepository;
    private final AppMessages messages;

    @Override
    public Asignatura crear(Asignatura asignatura) {
        log.debug("Persistiendo asignatura: {}", asignatura.getNombre());
        ProgramaEntity programa = programaRepository.findById(asignatura.getProgramaId())
            .orElseThrow(() -> new RuntimeException(
                String.format(messages.getAsignaturaProgramaNoEncontrado(), asignatura.getProgramaId())));
        AsignaturaEntity entity = AsignaturaEntity.builder()
            .nombre(asignatura.getNombre())
            .creditos(asignatura.getCreditos())
            .semestre(asignatura.getSemestre())
            .programa(programa)
            .build();
        AsignaturaEntity saved = asignaturaRepository.save(entity);
        log.info("Asignatura persistida con id={}", saved.getId());
        return toDomain(saved);
    }

    @Override
    public List<Asignatura> findAll() {
        log.debug("Consultando todas las asignaturas");
        List<Asignatura> result = asignaturaRepository.findAll().stream().map(this::toDomain).toList();
        log.info("findAll - {} asignaturas encontradas", result.size());
        return result;
    }

    @Override
    public Optional<Asignatura> findById(Long id) {
        log.debug("Consultando asignatura por id={}", id);
        Optional<Asignatura> result = asignaturaRepository.findById(id).map(this::toDomain);
        result.ifPresentOrElse(
            a -> log.info("findById - Asignatura encontrada id={}", id),
            () -> log.warn("findById - No existe asignatura con id={}", id)
        );
        return result;
    }

    @Override
    public List<Asignatura> findByPrograma(Long programaId) {
        log.debug("Consultando asignaturas por programaId={}", programaId);
        List<Asignatura> result = asignaturaRepository.findByProgramaId(programaId).stream().map(this::toDomain).toList();
        log.info("findByPrograma - {} asignaturas para programaId={}", result.size(), programaId);
        return result;
    }

    @Override
    public Asignatura actualizar(Long id, Asignatura asignatura) {
        log.debug("Actualizando asignatura id={}", id);
        AsignaturaEntity entity = asignaturaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                String.format(messages.getAsignaturaNoEncontrada(), id)));
        ProgramaEntity programa = programaRepository.findById(asignatura.getProgramaId())
            .orElseThrow(() -> new RuntimeException(
                String.format(messages.getAsignaturaProgramaNoEncontrado(), asignatura.getProgramaId())));
        entity.setNombre(asignatura.getNombre());
        entity.setCreditos(asignatura.getCreditos());
        entity.setSemestre(asignatura.getSemestre());
        entity.setPrograma(programa);
        Asignatura result = toDomain(asignaturaRepository.save(entity));
        log.info("Asignatura actualizada id={}", id);
        return result;
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Eliminando asignatura id={}", id);
        if (!asignaturaRepository.existsById(id)) {
            throw new RuntimeException(String.format(messages.getAsignaturaNoEncontrada(), id));
        }
        asignaturaRepository.deleteById(id);
        log.info("Asignatura eliminada id={}", id);
    }

    @Override
    public boolean existsByNombreAndProgramaId(String nombre, Long programaId) {
        return asignaturaRepository.existsByNombreAndProgramaId(nombre, programaId);
    }

    @Override
    public boolean existsByNombreAndProgramaIdAndIdNot(String nombre, Long programaId, Long id) {
        return asignaturaRepository.existsByNombreAndProgramaIdAndIdNot(nombre, programaId, id);
    }

    private Asignatura toDomain(AsignaturaEntity entity) {
        return Asignatura.builder()
            .id(entity.getId())
            .nombre(entity.getNombre())
            .creditos(entity.getCreditos())
            .semestre(entity.getSemestre())
            .programaId(entity.getPrograma().getId())
            .programaNombre(entity.getPrograma().getNombre())
            .build();
    }
}
