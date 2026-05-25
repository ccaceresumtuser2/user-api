package com.empresa.api.domain.service;

import com.empresa.api.domain.model.Programa;
import com.empresa.api.infrastructure.config.AppMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ProgramaService {

    private static final Logger log = LoggerFactory.getLogger(ProgramaService.class);

    private static final int MAX_SEMESTRES = 12;

    private final AppMessages messages;

    public ProgramaService(AppMessages messages) {
        this.messages = messages;
    }

    public void validar(Programa programa) {
        log.debug("Validando reglas de negocio para programa: {}", programa.getNombre());
        List<String> errores = new ArrayList<>();

        if (programa.getNombre() == null || programa.getNombre().isBlank())
            errores.add(messages.getProgramaNombreObligatorio());
        if (programa.getDuracionSemestres() == null || programa.getDuracionSemestres() <= 0)
            errores.add(messages.getProgramaSemestresPositivo());
        if (programa.getDuracionSemestres() != null && programa.getDuracionSemestres() > MAX_SEMESTRES)
            errores.add(String.format(messages.getProgramaSemestresMaximo(), MAX_SEMESTRES));
        if (programa.getFacultadId() == null)
            errores.add(messages.getProgramaFacultadObligatorio());

        if (!errores.isEmpty()) {
            String mensaje = String.join(", ", errores);
            log.warn("Validacion de negocio fallida para programa: {}", mensaje);
            throw new IllegalArgumentException(mensaje);
        }
        log.debug("Programa validado correctamente: {}", programa.getNombre());
    }

    public void validarDuplicado(boolean existe, String nombre, Long facultadId) {
        if (existe) {
            log.warn("Intento de insertar programa duplicado: {} en facultadId={}", nombre, facultadId);
            throw new IllegalArgumentException(
                String.format(messages.getProgramaDuplicado(), nombre, facultadId));
        }
    }

    public void validarNoVacia(List<Programa> programas, Long facultadId) {
        if (programas.isEmpty()) {
            log.warn("No se encontraron programas para facultadId={}", facultadId);
            throw new IllegalArgumentException(
                String.format(messages.getProgramaListaVacia(), facultadId));
        }
    }
}
