package com.empresa.api.domain.service;

import com.empresa.api.domain.model.Asignatura;
import com.empresa.api.infrastructure.config.AppMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AsignaturaService {

    private static final Logger log = LoggerFactory.getLogger(AsignaturaService.class);

    private static final int MAX_CREDITOS = 10;
    private static final int MAX_SEMESTRE = 12;

    private final AppMessages messages;

    public AsignaturaService(AppMessages messages) {
        this.messages = messages;
    }

    public void validar(Asignatura asignatura) {
        log.debug("Validando reglas de negocio para asignatura: {}", asignatura.getNombre());
        List<String> errores = new ArrayList<>();

        if (asignatura.getNombre() == null || asignatura.getNombre().isBlank())
            errores.add(messages.getAsignaturaNombreObligatorio());
        if (asignatura.getCreditos() == null || asignatura.getCreditos() <= 0)
            errores.add(messages.getAsignaturaCreditosPositivo());
        if (asignatura.getCreditos() != null && asignatura.getCreditos() > MAX_CREDITOS)
            errores.add(String.format(messages.getAsignaturaCreditosMaximo(), MAX_CREDITOS));
        if (asignatura.getSemestre() == null || asignatura.getSemestre() <= 0)
            errores.add(messages.getAsignaturaSemestrePositivo());
        if (asignatura.getSemestre() != null && asignatura.getSemestre() > MAX_SEMESTRE)
            errores.add(String.format(messages.getAsignaturaSemestreMaximo(), MAX_SEMESTRE));
        if (asignatura.getProgramaId() == null)
            errores.add(messages.getAsignaturaProgramaObligatorio());

        if (!errores.isEmpty()) {
            String mensaje = String.join(", ", errores);
            log.warn("Validacion de negocio fallida para asignatura: {}", mensaje);
            throw new IllegalArgumentException(mensaje);
        }
        log.debug("Asignatura validada correctamente: {}", asignatura.getNombre());
    }

    public void validarDuplicado(boolean existe, String nombre, Long programaId) {
        if (existe) {
            log.warn("Intento de insertar asignatura duplicada: {} en programaId={}", nombre, programaId);
            throw new IllegalArgumentException(
                String.format(messages.getAsignaturaDuplicado(), nombre, programaId));
        }
    }

    public void validarNoVacia(List<Asignatura> asignaturas, Long programaId) {
        if (asignaturas.isEmpty()) {
            log.warn("No se encontraron asignaturas para programaId={}", programaId);
            throw new IllegalArgumentException(
                String.format(messages.getAsignaturaListaVacia(), programaId));
        }
    }
}
