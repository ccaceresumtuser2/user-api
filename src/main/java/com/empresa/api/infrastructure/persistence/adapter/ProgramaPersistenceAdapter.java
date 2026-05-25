package com.empresa.api.infrastructure.persistence.adapter;

import com.empresa.api.application.port.out.ProgramaPortOut;
import com.empresa.api.domain.model.Programa;
import com.empresa.api.infrastructure.config.AppMessages;
import com.empresa.api.infrastructure.persistence.entity.FacultadEntity;
import com.empresa.api.infrastructure.persistence.entity.ProgramaEntity;
import com.empresa.api.infrastructure.persistence.repository.FacultadRepository;
import com.empresa.api.infrastructure.persistence.repository.ProgramaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class ProgramaPersistenceAdapter implements ProgramaPortOut {

    private final ProgramaRepository programaRepository;
    private final FacultadRepository facultadRepository;
    private final AppMessages messages;

    @Override
    public Programa crear(Programa programa) {
        log.debug("Persistiendo programa: {}", programa.getNombre());
        FacultadEntity facultad = facultadRepository.findById(programa.getFacultadId())
            .orElseThrow(() -> new RuntimeException(
                String.format(messages.getProgramaFacultadNoEncontrada(), programa.getFacultadId())));
        ProgramaEntity entity = ProgramaEntity.builder()
            .nombre(programa.getNombre())
            .duracionSemestres(programa.getDuracionSemestres())
            .facultad(facultad)
            .build();
        ProgramaEntity saved = programaRepository.save(entity);
        log.info("Programa persistido con id={}", saved.getId());
        return toDomain(saved);
    }

    @Override
    public List<Programa> findAll() {
        log.debug("Consultando todos los programas");
        List<Programa> result = programaRepository.findAll().stream().map(this::toDomain).toList();
        log.info("findAll - {} programas encontrados", result.size());
        return result;
    }

    @Override
    public Optional<Programa> findById(Long id) {
        log.debug("Consultando programa por id={}", id);
        Optional<Programa> result = programaRepository.findById(id).map(this::toDomain);
        result.ifPresentOrElse(
            p -> log.info("findById - Programa encontrado id={}", id),
            () -> log.warn("findById - No existe programa con id={}", id)
        );
        return result;
    }

    @Override
    public List<Programa> findByFacultad(Long facultadId) {
        log.debug("Consultando programas por facultadId={}", facultadId);
        List<Programa> result = programaRepository.findByFacultadId(facultadId).stream().map(this::toDomain).toList();
        log.info("findByFacultad - {} programas para facultadId={}", result.size(), facultadId);
        return result;
    }

    @Override
    public Programa actualizar(Long id, Programa programa) {
        log.debug("Actualizando programa id={}", id);
        ProgramaEntity entity = programaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                String.format(messages.getProgramaNoEncontrado(), id)));
        FacultadEntity facultad = facultadRepository.findById(programa.getFacultadId())
            .orElseThrow(() -> new RuntimeException(
                String.format(messages.getProgramaFacultadNoEncontrada(), programa.getFacultadId())));
        entity.setNombre(programa.getNombre());
        entity.setDuracionSemestres(programa.getDuracionSemestres());
        entity.setFacultad(facultad);
        Programa result = toDomain(programaRepository.save(entity));
        log.info("Programa actualizado id={}", id);
        return result;
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Eliminando programa id={}", id);
        if (!programaRepository.existsById(id)) {
            throw new RuntimeException(String.format(messages.getProgramaNoEncontrado(), id));
        }
        programaRepository.deleteById(id);
        log.info("Programa eliminado id={}", id);
    }

    @Override
    public boolean existsByNombreAndFacultadId(String nombre, Long facultadId) {
        return programaRepository.existsByNombreAndFacultadId(nombre, facultadId);
    }

    @Override
    public boolean existsByNombreAndFacultadIdAndIdNot(String nombre, Long facultadId, Long id) {
        return programaRepository.existsByNombreAndFacultadIdAndIdNot(nombre, facultadId, id);
    }

    private Programa toDomain(ProgramaEntity entity) {
        return Programa.builder()
            .id(entity.getId())
            .nombre(entity.getNombre())
            .duracionSemestres(entity.getDuracionSemestres())
            .facultadId(entity.getFacultad().getId())
            .facultadNombre(entity.getFacultad().getNombre())
            .build();
    }
}
