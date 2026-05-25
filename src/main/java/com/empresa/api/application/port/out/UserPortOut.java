package com.empresa.api.application.port.out;

import java.util.List;
import java.util.Optional;

import com.empresa.api.domain.model.User;

public interface UserPortOut {
    User save(User user);
    List<User> findAll();
    Optional<User> findByEmail(String email);
    List<User> buscarPorNombre(String nombre);
    List<User> buscarPorApellido(String apellido);
    List<User> buscarPorEdad(String edad);
    long count();
}
