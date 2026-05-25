package com.empresa.api.application.port.in;

import java.util.List;
import java.util.Optional;

import com.empresa.api.domain.model.User;

public interface UserPortIn {
    String createUser(User userData);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    List<User> buscarPorNombre(String nombre);
    List<User> buscarPorApellido(String apellido);
    List<User> buscarPorEdad(String edad);
    long count();
}
