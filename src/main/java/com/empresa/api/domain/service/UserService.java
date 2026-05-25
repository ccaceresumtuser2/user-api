package com.empresa.api.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.empresa.api.domain.model.User;

/**
 * @author Carlos Jose Caceres Ochoa
 * Servicio de dominio para gestionar las operaciones relacionadas con los usuarios.
 */
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public String createUser(User userData) {
        log.debug("Iniciando creación de usuario");
        if (userData == null) {
            log.error("Los datos del usuario son nulos");
            return "Error: User data is null";
        }
        String validationResult = validarUsuario(userData);
        if (validationResult.equals("Usuario Validado con Exito")) {
            log.info("Usuario validado correctamente: {}", userData.getEmail());
            return "User created successfully: " + userData.toString();
        } else {
            log.warn("Validación fallida: {}", validationResult);
            return validationResult;
        }
    }

    public String validarUsuario(User userData) {
        log.debug("Validando campos del usuario");
        List<String> errors = new ArrayList<>();

        if (userData.getEmail() == null || userData.getEmail().isEmpty())
            errors.add("Email is required");
        if (userData.getNombres() == null || userData.getNombres().isEmpty())
            errors.add("First name is required");
        if (userData.getApellidos() == null || userData.getApellidos().isEmpty())
            errors.add("Last name is required");
        if (userData.getEdad() == null || userData.getEdad().isEmpty())
            errors.add("Age is required");

        if (!errors.isEmpty()) {
            String errores = String.join(", ", errors);
            log.warn("Errores de validación: {}", errores);
            return "Error al Validar Usuario: " + errores;
        }
        return "Usuario Validado con Exito";
    }
}
