package com.empresa.api.infrastructure.persistence.adapter;

import com.empresa.api.application.port.out.FacultadPortOut;
import com.empresa.api.domain.model.Facultad;
import com.empresa.api.infrastructure.config.AppMessages;
import com.empresa.api.infrastructure.persistence.entity.FacultadEntity;
import com.empresa.api.infrastructure.persistence.repository.FacultadRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class FacultadPersistenceAdapter implements FacultadPortOut {

    private final FacultadRepository facultadRepository;
    private final AppMessages messages;

    @Override
    public Facultad crear(Facultad facultad) {
        log.debug("Persistiendo facultad: {}", facultad.getNombre());
        FacultadEntity saved = facultadRepository.save(toEntity(facultad));
        log.info("Facultad persistida con id={}", saved.getId());
        return toDomain(saved);
    }

    @Override
    public List<Facultad> findAll() {
        log.debug("Consultando todas las facultades");
        List<Facultad> result = facultadRepository.findAll().stream().map(this::toDomain).toList();
        log.info("findAll - {} facultades encontradas", result.size());
        return result;
    }

    @Override
    public Optional<Facultad> findById(Long id) {
        log.debug("Consultando facultad por id={}", id);
        Optional<Facultad> result = facultadRepository.findById(id).map(this::toDomain);
        result.ifPresentOrElse(
            f -> log.info("findById - Facultad encontrada id={}", id),
            () -> log.warn("findById - No existe facultad con id={}", id)
        );
        return result;
    }

    @Override
    public Facultad actualizar(Long id, Facultad facultad) {
        log.debug("Actualizando facultad id={}", id);
        FacultadEntity entity = facultadRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                String.format(messages.getFacultadNoEncontrada(), id)));
        entity.setNombre(facultad.getNombre());
        entity.setDecano(facultad.getDecano());
        Facultad result = toDomain(facultadRepository.save(entity));
        log.info("Facultad actualizada id={}", id);
        return result;
    }

    @Override
    public void eliminar(Long id) {
        log.debug("Eliminando facultad id={}", id);
        if (!facultadRepository.existsById(id)) {
            throw new RuntimeException(String.format(messages.getFacultadNoEncontrada(), id));
        }
        facultadRepository.deleteById(id);
        log.info("Facultad eliminada id={}", id);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return facultadRepository.existsByNombre(nombre);
    }

    @Override
    public boolean existsByNombreAndIdNot(String nombre, Long id) {
        return facultadRepository.existsByNombreAndIdNot(nombre, id);
    }

    private FacultadEntity toEntity(Facultad facultad) {
        return FacultadEntity.builder()
            .nombre(facultad.getNombre())
            .decano(facultad.getDecano())
            .build();
    }

    private Facultad toDomain(FacultadEntity entity) {
        return Facultad.builder()
            .id(entity.getId())
            .nombre(entity.getNombre())
            .decano(entity.getDecano())
            .build();
    }
}
