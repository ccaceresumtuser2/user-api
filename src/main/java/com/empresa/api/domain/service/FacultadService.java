package com.empresa.api.domain.service;

import com.empresa.api.domain.model.Facultad;
import com.empresa.api.infrastructure.config.AppMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FacultadService {

    private static final Logger log = LoggerFactory.getLogger(FacultadService.class);

    private final AppMessages messages;

    public FacultadService(AppMessages messages) {
        this.messages = messages;
    }

    public void validar(Facultad facultad) {
        log.debug("Validando reglas de negocio para facultad: {}", facultad.getNombre());
        List<String> errores = new ArrayList<>();

        if (facultad.getNombre() == null || facultad.getNombre().isBlank())
            errores.add(messages.getFacultadNombreObligatorio());
        if (facultad.getDecano() == null || facultad.getDecano().isBlank())
            errores.add(messages.getFacultadDecanoObligatorio());
        if (facultad.getNombre() != null && facultad.getNombre().length() < 3)
            errores.add(messages.getFacultadNombreMinimo());

        if (!errores.isEmpty()) {
            String mensaje = String.join(", ", errores);
            log.warn("Validacion de negocio fallida para facultad: {}", mensaje);
            throw new IllegalArgumentException(mensaje);
        }
        log.debug("Facultad validada correctamente: {}", facultad.getNombre());
    }

    public void validarDuplicado(boolean existe, String nombre) {
        if (existe) {
            log.warn("Intento de insertar facultad duplicada: {}", nombre);
            throw new IllegalArgumentException(String.format(messages.getFacultadDuplicado(), nombre));
        }
    }

    public void validarNoVacia(List<Facultad> facultades) {
        if (facultades.isEmpty()) {
            log.warn("La consulta de facultades retorno una lista vacia");
            throw new IllegalArgumentException(messages.getFacultadListaVacia());
        }
    }
}
