package com.empresa.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.empresa.api.application.port.out.UserPortOut;
import com.empresa.api.domain.model.User;
import com.empresa.api.infrastructure.persistence.entity.UserEntity;
import com.empresa.api.infrastructure.persistence.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class UserPersistenceAdapter implements UserPortOut {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        log.debug("Persistiendo usuario: {}", user.getEmail());
        UserEntity saved = userRepository.save(toEntity(user));
        log.info("Usuario persistido con id={}", saved.getId());
        return toDomain(saved);
    }

    @Override
    public List<User> findAll() {
        log.debug("Consultando todos los usuarios");
        List<User> usuarios = userRepository.findAll().stream().map(this::toDomain).toList();
        log.info("findAll - {} usuarios encontrados", usuarios.size());
        return usuarios;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.debug("Consultando usuario por email: {}", email);
        Optional<User> resultado = userRepository.findByEmail(email).map(this::toDomain);
        resultado.ifPresentOrElse(
            u -> log.info("findByEmail - Usuario encontrado: {}", email),
            () -> log.warn("findByEmail - No existe usuario con email: {}", email)
        );
        return resultado;
    }

    @Override
    public List<User> buscarPorNombre(String nombre) {
        log.debug("Buscando usuarios por nombre: {}", nombre);
        List<User> resultado = userRepository.findByNombresContainingIgnoreCase(nombre).stream().map(this::toDomain).toList();
        log.info("buscarPorNombre - {} resultado(s) para '{}'", resultado.size(), nombre);
        return resultado;
    }

    @Override
    public List<User> buscarPorApellido(String apellido) {
        log.debug("Buscando usuarios por apellido: {}", apellido);
        List<User> resultado = userRepository.buscarPorApellido(apellido).stream().map(this::toDomain).toList();
        log.info("buscarPorApellido - {} resultado(s) para '{}'", resultado.size(), apellido);
        return resultado;
    }

    @Override
    public List<User> buscarPorEdad(String edad) {
        log.debug("Buscando usuarios por edad: {}", edad);
        List<User> resultado = userRepository.buscarPorEdadNativo(edad).stream().map(this::toDomain).toList();
        log.info("buscarPorEdad - {} resultado(s) para edad '{}'", resultado.size(), edad);
        return resultado;
    }

    @Override
    public long count() {
        long total = userRepository.count();
        log.info("count - Total de usuarios en BD: {}", total);
        return total;
    }

    private UserEntity toEntity(User user) {
        return UserEntity.builder()
                .nombres(user.getNombres())
                .apellidos(user.getApellidos())
                .email(user.getEmail())
                .edad(user.getEdad())
                .build();
    }

    private User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .nombres(entity.getNombres())
                .apellidos(entity.getApellidos())
                .email(entity.getEmail())
                .edad(entity.getEdad())
                .build();
    }
}
