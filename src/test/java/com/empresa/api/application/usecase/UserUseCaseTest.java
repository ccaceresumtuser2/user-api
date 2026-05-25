package com.empresa.api.application.usecase;

import com.empresa.api.application.port.out.UserPortOut;
import com.empresa.api.domain.model.User;
import com.empresa.api.domain.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private UserService userService;

    @Mock
    private UserPortOut userPortOut;

    @InjectMocks
    private UserUseCase userUseCase;

    private User buildUser() {
        return User.builder()
                .id(1L)
                .nombres("Carlos")
                .apellidos("Caceres")
                .email("carlos@test.com")
                .edad("25")
                .build();
    }

    // ── createUser ──────────────────────────────────────────────────────────

    @Test
    void createUser_whenValidationPasses_savesAndReturnsSuccess() {
        User user = buildUser();
        when(userService.createUser(user)).thenReturn("User created successfully");
        when(userPortOut.save(user)).thenReturn(user);

        String result = userUseCase.createUser(user);

        assertEquals("User created successfully", result);
        verify(userPortOut, times(1)).save(user);
    }

    @Test
    void createUser_whenValidationFails_doesNotSave() {
        User user = buildUser();
        when(userService.createUser(user)).thenReturn("Error al Validar Usuario: Email is required");

        String result = userUseCase.createUser(user);

        assertTrue(result.startsWith("Error"));
        verify(userPortOut, never()).save(any());
    }

    // ── findAll ─────────────────────────────────────────────────────────────

    @Test
    void findAll_returnsListFromPortOut() {
        List<User> users = List.of(buildUser());
        when(userPortOut.findAll()).thenReturn(users);

        List<User> result = userUseCase.findAll();

        assertEquals(1, result.size());
        verify(userPortOut, times(1)).findAll();
    }

    @Test
    void findAll_whenEmpty_returnsEmptyList() {
        when(userPortOut.findAll()).thenReturn(List.of());

        List<User> result = userUseCase.findAll();

        assertTrue(result.isEmpty());
    }

    // ── findByEmail ──────────────────────────────────────────────────────────

    @Test
    void findByEmail_whenExists_returnsUser() {
        User user = buildUser();
        when(userPortOut.findByEmail("carlos@test.com")).thenReturn(Optional.of(user));

        Optional<User> result = userUseCase.findByEmail("carlos@test.com");

        assertTrue(result.isPresent());
        assertEquals("carlos@test.com", result.get().getEmail());
    }

    @Test
    void findByEmail_whenNotExists_returnsEmpty() {
        when(userPortOut.findByEmail("noexiste@test.com")).thenReturn(Optional.empty());

        Optional<User> result = userUseCase.findByEmail("noexiste@test.com");

        assertFalse(result.isPresent());
    }

    // ── buscarPorNombre ──────────────────────────────────────────────────────

    @Test
    void buscarPorNombre_returnsMatchingUsers() {
        when(userPortOut.buscarPorNombre("Carlos")).thenReturn(List.of(buildUser()));

        List<User> result = userUseCase.buscarPorNombre("Carlos");

        assertEquals(1, result.size());
        verify(userPortOut).buscarPorNombre("Carlos");
    }

    // ── buscarPorApellido ────────────────────────────────────────────────────

    @Test
    void buscarPorApellido_returnsMatchingUsers() {
        when(userPortOut.buscarPorApellido("Caceres")).thenReturn(List.of(buildUser()));

        List<User> result = userUseCase.buscarPorApellido("Caceres");

        assertEquals(1, result.size());
        verify(userPortOut).buscarPorApellido("Caceres");
    }

    // ── buscarPorEdad ────────────────────────────────────────────────────────

    @Test
    void buscarPorEdad_returnsMatchingUsers() {
        when(userPortOut.buscarPorEdad("25")).thenReturn(List.of(buildUser()));

        List<User> result = userUseCase.buscarPorEdad("25");

        assertEquals(1, result.size());
        verify(userPortOut).buscarPorEdad("25");
    }

    // ── count ────────────────────────────────────────────────────────────────

    @Test
    void count_returnsTotalFromPortOut() {
        when(userPortOut.count()).thenReturn(5L);

        long result = userUseCase.count();

        assertEquals(5L, result);
        verify(userPortOut, times(1)).count();
    }
}
