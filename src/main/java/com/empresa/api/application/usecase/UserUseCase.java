package com.empresa.api.application.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.empresa.api.application.port.in.UserPortIn;
import com.empresa.api.application.port.out.UserPortOut;
import com.empresa.api.domain.model.User;
import com.empresa.api.domain.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserUseCase implements UserPortIn {
    private final UserService userService;
    private final UserPortOut userPortOut;

    @Override
    public String createUser(User userData) {
        String result = userService.createUser(userData);
        if (!result.startsWith("Error")) {
            userPortOut.save(userData);
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        return userPortOut.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userPortOut.findByEmail(email);
    }

    @Override
    public List<User> buscarPorNombre(String nombre) {
        return userPortOut.buscarPorNombre(nombre);
    }

    @Override
    public List<User> buscarPorApellido(String apellido) {
        return userPortOut.buscarPorApellido(apellido);
    }

    @Override
    public List<User> buscarPorEdad(String edad) {
        return userPortOut.buscarPorEdad(edad);
    }

    @Override
    public long count() {
        return userPortOut.count();
    }
}
