package com.empresa.api.domain.service;

import com.empresa.api.domain.model.Author;
import com.empresa.api.infrastructure.config.AppMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorService.class);

    private final AppMessages messages;

    public AuthorService(AppMessages messages) {
        this.messages = messages;
    }

    public void validar(Author author) {
        log.debug("Validando reglas de negocio para autor: {}", author.getNombre());
        List<String> errores = new ArrayList<>();

        if (author.getNombre() == null || author.getNombre().isBlank())
            errores.add(messages.getAuthorNombreObligatorio());
        if (author.getNombre() != null && author.getNombre().length() < 2)
            errores.add(messages.getAuthorNombreMinimo());
        if (author.getNacionalidad() == null || author.getNacionalidad().isBlank())
            errores.add(messages.getAuthorNacionalidadObligatorio());

        if (!errores.isEmpty()) {
            String mensaje = String.join(", ", errores);
            log.warn("Validacion de negocio fallida para autor: {}", mensaje);
            throw new IllegalArgumentException(mensaje);
        }
        log.debug("Autor validado correctamente: {}", author.getNombre());
    }

    public void validarDuplicado(boolean existe, String nombre) {
        if (existe) {
            log.warn("Intento de insertar autor duplicado: {}", nombre);
            throw new IllegalArgumentException(
                String.format(messages.getAuthorDuplicado(), nombre));
        }
    }

    public void validarNoVacia(List<Author> authors) {
        if (authors.isEmpty()) {
            log.warn("La consulta de autores retorno una lista vacia");
            throw new IllegalArgumentException(messages.getAuthorListaVacia());
        }
    }
}
