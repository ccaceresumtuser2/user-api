package com.empresa.api.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.empresa.api.application.port.out.UserPortOut;
import com.empresa.api.domain.model.User;
import com.empresa.api.infrastructure.persistence.entity.UserEntity;
import com.empresa.api.infrastructure.persistence.repository.UserRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UserPersistenceAdapter implements UserPortOut {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        UserEntity saved = userRepository.save(toEntity(user));
        return toDomain(saved);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public List<User> buscarPorNombre(String nombre) {
        return userRepository.findByNombresContainingIgnoreCase(nombre).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<User> buscarPorApellido(String apellido) {
        return userRepository.buscarPorApellido(apellido).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<User> buscarPorEdad(String edad) {
        return userRepository.buscarPorEdadNativo(edad).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return userRepository.count();
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
