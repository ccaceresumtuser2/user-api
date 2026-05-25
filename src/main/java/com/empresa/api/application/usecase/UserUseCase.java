package com.empresa.api.application.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.empresa.api.application.port.in.UserPortIn;
import com.empresa.api.application.port.out.UserPortOut;
import com.empresa.api.domain.model.User;
import com.empresa.api.domain.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserUseCase implements UserPortIn {
    private final UserService userService;
    private final UserPortOut userPortOut;

    @Override
    public String createUser(User userData) {
        log.debug("Ejecutando caso de uso: createUser para email={}", userData != null ? userData.getEmail() : "null");
        String result = userService.createUser(userData);
        if (!result.startsWith("Error")) {
            userPortOut.save(userData);
            log.debug("Usuario guardado en persistencia: {}", userData.getEmail());
        } else {
            log.warn("Validación fallida en createUser: {}", result);
        }
        return result;
    }

    @Override
    public List<User> findAll() {
        log.debug("Ejecutando caso de uso: findAll");
        return userPortOut.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.debug("Ejecutando caso de uso: findByEmail email={}", email);
        return userPortOut.findByEmail(email);
    }

    @Override
    public List<User> buscarPorNombre(String nombre) {
        log.debug("Ejecutando caso de uso: buscarPorNombre nombre={}", nombre);
        return userPortOut.buscarPorNombre(nombre);
    }

    @Override
    public List<User> buscarPorApellido(String apellido) {
        log.debug("Ejecutando caso de uso: buscarPorApellido apellido={}", apellido);
        return userPortOut.buscarPorApellido(apellido);
    }

    @Override
    public List<User> buscarPorEdad(String edad) {
        log.debug("Ejecutando caso de uso: buscarPorEdad edad={}", edad);
        return userPortOut.buscarPorEdad(edad);
    }

    @Override
    public long count() {
        log.debug("Ejecutando caso de uso: count");
        return userPortOut.count();
    }
}
