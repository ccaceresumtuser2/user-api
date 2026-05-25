package com.empresa.api.infrastructure.persistence.adapter;

import com.empresa.api.domain.model.User;
import com.empresa.api.infrastructure.persistence.entity.UserEntity;
import com.empresa.api.infrastructure.persistence.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserPersistenceAdapter adapter;

    private UserEntity buildEntity() {
        return UserEntity.builder()
                .id(1L)
                .nombres("Carlos")
                .apellidos("Caceres")
                .email("carlos@test.com")
                .edad("25")
                .build();
    }

    private User buildUser() {
        return User.builder()
                .nombres("Carlos")
                .apellidos("Caceres")
                .email("carlos@test.com")
                .edad("25")
                .build();
    }

    // ── save ─────────────────────────────────────────────────────────────────

    @Test
    void save_persistsAndReturnsDomainUser() {
        UserEntity entity = buildEntity();
        when(userRepository.save(any(UserEntity.class))).thenReturn(entity);

        User result = adapter.save(buildUser());

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("carlos@test.com", result.getEmail());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    // ── findAll ───────────────────────────────────────────────────────────────

    @Test
    void findAll_returnsAllUsersAsDomain() {
        when(userRepository.findAll()).thenReturn(List.of(buildEntity()));

        List<User> result = adapter.findAll();

        assertEquals(1, result.size());
        assertEquals("Carlos", result.get(0).getNombres());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void findAll_whenEmpty_returnsEmptyList() {
        when(userRepository.findAll()).thenReturn(List.of());

        List<User> result = adapter.findAll();

        assertTrue(result.isEmpty());
    }

    // ── findByEmail ───────────────────────────────────────────────────────────

    @Test
    void findByEmail_whenExists_returnsDomainUser() {
        when(userRepository.findByEmail("carlos@test.com")).thenReturn(Optional.of(buildEntity()));

        Optional<User> result = adapter.findByEmail("carlos@test.com");

        assertTrue(result.isPresent());
        assertEquals("carlos@test.com", result.get().getEmail());
    }

    @Test
    void findByEmail_whenNotExists_returnsEmpty() {
        when(userRepository.findByEmail("noexiste@test.com")).thenReturn(Optional.empty());

        Optional<User> result = adapter.findByEmail("noexiste@test.com");

        assertFalse(result.isPresent());
    }

    // ── buscarPorNombre ────────────────────────────────────────────────────────

    @Test
    void buscarPorNombre_returnsMatchingUsers() {
        when(userRepository.findByNombresContainingIgnoreCase("Carlos")).thenReturn(List.of(buildEntity()));

        List<User> result = adapter.buscarPorNombre("Carlos");

        assertEquals(1, result.size());
        assertEquals("Carlos", result.get(0).getNombres());
    }

    @Test
    void buscarPorNombre_whenNoMatch_returnsEmpty() {
        when(userRepository.findByNombresContainingIgnoreCase("XYZ")).thenReturn(List.of());

        List<User> result = adapter.buscarPorNombre("XYZ");

        assertTrue(result.isEmpty());
    }

    // ── buscarPorApellido ──────────────────────────────────────────────────────

    @Test
    void buscarPorApellido_returnsMatchingUsers() {
        when(userRepository.buscarPorApellido("Caceres")).thenReturn(List.of(buildEntity()));

        List<User> result = adapter.buscarPorApellido("Caceres");

        assertEquals(1, result.size());
        assertEquals("Caceres", result.get(0).getApellidos());
    }

    // ── buscarPorEdad ──────────────────────────────────────────────────────────

    @Test
    void buscarPorEdad_returnsMatchingUsers() {
        when(userRepository.buscarPorEdadNativo("25")).thenReturn(List.of(buildEntity()));

        List<User> result = adapter.buscarPorEdad("25");

        assertEquals(1, result.size());
        assertEquals("25", result.get(0).getEdad());
    }

    // ── count ──────────────────────────────────────────────────────────────────

    @Test
    void count_returnsTotalFromRepository() {
        when(userRepository.count()).thenReturn(10L);

        long result = adapter.count();

        assertEquals(10L, result);
        verify(userRepository, times(1)).count();
    }
}
