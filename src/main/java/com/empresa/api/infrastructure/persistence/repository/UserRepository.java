package com.empresa.api.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.empresa.api.infrastructure.persistence.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 1. Consulta derivada por nombre de campo
    Optional<UserEntity> findByEmail(String email);

    // 2. Consulta derivada con búsqueda parcial (LIKE %nombre%)
    List<UserEntity> findByNombresContainingIgnoreCase(String nombres);

    // 3. JPQL con @Query — buscar por apellido exacto
    @Query("SELECT u FROM UserEntity u WHERE u.apellidos = :apellidos")
    List<UserEntity> buscarPorApellido(@Param("apellidos") String apellidos);

    // 4. SQL nativo con @Query
    @Query(value = "SELECT * FROM users WHERE edad = :edad", nativeQuery = true)
    List<UserEntity> buscarPorEdadNativo(@Param("edad") String edad);
}
