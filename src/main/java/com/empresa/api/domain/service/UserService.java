package com.empresa.api.domain.service;

import java.util.ArrayList;
import java.util.List;

import com.empresa.api.domain.model.User;

/**
 * @author Carlos Jose Caceres Ochoa
 * Servicio de dominio para gestionar las operaciones relacionadas con los usuarios.
 */
public class UserService {
    public String createUser(User userData) {
       if(userData == null) {
            return "Error: User data is null";
        }
        else{
            String validationResult = validarUsuario(userData);
            if(validationResult.equals("Usuario Validado con Exito")) {
                // Here you would typically save the user data to a database or perform other business logic
                return "User created successfully: " + userData.toString();
            } else {
                return validationResult;
            }   
        }
     }

    public String validarUsuario(User userData) {
        // Implementation for validating user data
        List<String> errors = new ArrayList<>();
        String errores="";
        if(userData.getEmail() == null || userData.getEmail().isEmpty()) {
            errors.add("Email is required");
        }
        if(userData.getNombres() == null || userData.getNombres().isEmpty()) {
            errors.add("First name is required");
        }       
        if(userData.getApellidos() == null || userData.getApellidos().isEmpty()) {
            errors.add("Last name is required");
        }
        if(userData.getEdad() == null || userData.getEdad().isEmpty()) {
            errors.add("Age is required");
        }

        if(!errors.isEmpty()) {
            errores=String.join(", ", errors);
        }
        return errores.isEmpty() ? "Usuario Validado con Exito" : "Error al Validar Usuario: " + errores  ;
    }
}
