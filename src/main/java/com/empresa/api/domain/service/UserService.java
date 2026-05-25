package com.empresa.api.domain.service;

import com.empresa.api.domain.model.User;
import com.empresa.api.infrastructure.config.AppMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final AppMessages messages;

    public UserService(AppMessages messages) {
        this.messages = messages;
    }

    public String createUser(User userData) {
        log.debug("Iniciando creacion de usuario");
        if (userData == null) {
            log.error("Los datos del usuario son nulos");
            return messages.getUserDatosNulos();
        }
        String validationResult = validarUsuario(userData);
        if (validationResult.equals(messages.getUserValidado())) {
            log.info("Usuario validado correctamente: {}", userData.getEmail());
            return String.format(messages.getUserCreado(), userData.toString());
        } else {
            log.warn("Validacion fallida: {}", validationResult);
            return validationResult;
        }
    }

    public String validarUsuario(User userData) {
        log.debug("Validando campos del usuario");
        List<String> errors = new ArrayList<>();

        if (userData.getEmail() == null || userData.getEmail().isEmpty())
            errors.add(messages.getUserEmailObligatorio());
        if (userData.getNombres() == null || userData.getNombres().isEmpty())
            errors.add(messages.getUserNombresObligatorio());
        if (userData.getApellidos() == null || userData.getApellidos().isEmpty())
            errors.add(messages.getUserApellidosObligatorio());
        if (userData.getEdad() == null || userData.getEdad().isEmpty())
            errors.add(messages.getUserEdadObligatorio());

        if (!errors.isEmpty()) {
            String errores = String.join(", ", errors);
            log.warn("Errores de validacion: {}", errores);
            return String.format(messages.getUserErrorValidacion(), errores);
        }
        return messages.getUserValidado();
    }
}
