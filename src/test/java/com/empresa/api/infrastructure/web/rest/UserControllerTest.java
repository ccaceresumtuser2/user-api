package com.empresa.api.infrastructure.web.rest;

import com.empresa.api.application.port.in.UserPortIn;
import com.empresa.api.domain.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserPortIn userPortIn;

    @InjectMocks
    private UserController userController;

    private User buildUser() {
        return User.builder()
                .id(1L)
                .nombres("Carlos")
                .apellidos("Caceres")
                .email("carlos@test.com")
                .edad("25")
                .build();
    }

    private UserRequest buildRequest() {
        return UserRequest.builder()
                .nombres("Carlos")
                .apellidos("Caceres")
                .email("carlos@test.com")
                .edad("25")
                .build();
    }

    // ── POST /new ─────────────────────────────────────────────────────────────

    @Test
    void crearUsuario_whenValid_returns200() {
        when(userPortIn.createUser(any(User.class))).thenReturn("User created successfully");

        ResponseEntity<UserResponse> response = userController.crearUsuario(buildRequest());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().getStatus());
    }

    @Test
    void crearUsuario_whenValidationFails_returns400() {
        when(userPortIn.createUser(any(User.class))).thenReturn("Error al Validar Usuario: Email is required");

        ResponseEntity<UserResponse> response = userController.crearUsuario(buildRequest());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("ERROR", response.getBody().getStatus());
    }

    // ── GET /list ─────────────────────────────────────────────────────────────

    @Test
    void listarUsuarios_returns200WithUsers() {
        when(userPortIn.findAll()).thenReturn(List.of(buildUser()));

        ResponseEntity<UserResponse> response = userController.listarUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().getStatus());
        verify(userPortIn, times(1)).findAll();
    }

    @Test
    void listarUsuarios_whenEmpty_returns200() {
        when(userPortIn.findAll()).thenReturn(List.of());

        ResponseEntity<UserResponse> response = userController.listarUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    // ── GET /email/{email} ────────────────────────────────────────────────────

    @Test
    void buscarPorEmail_whenExists_returns200() {
        when(userPortIn.findByEmail("carlos@test.com")).thenReturn(Optional.of(buildUser()));

        ResponseEntity<UserResponse> response = userController.buscarPorEmail("carlos@test.com");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().getStatus());
    }

    @Test
    void buscarPorEmail_whenNotExists_returns404() {
        when(userPortIn.findByEmail(anyString())).thenReturn(Optional.empty());

        ResponseEntity<UserResponse> response = userController.buscarPorEmail("noexiste@test.com");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ERROR", response.getBody().getStatus());
    }

    // ── GET /buscar/nombre ────────────────────────────────────────────────────

    @Test
    void buscarPorNombre_returns200WithResults() {
        when(userPortIn.buscarPorNombre("Carlos")).thenReturn(List.of(buildUser()));

        ResponseEntity<UserResponse> response = userController.buscarPorNombre("Carlos");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().getStatus());
    }

    // ── GET /buscar/apellido ──────────────────────────────────────────────────

    @Test
    void buscarPorApellido_returns200WithResults() {
        when(userPortIn.buscarPorApellido("Caceres")).thenReturn(List.of(buildUser()));

        ResponseEntity<UserResponse> response = userController.buscarPorApellido("Caceres");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().getStatus());
    }

    // ── GET /buscar/edad ──────────────────────────────────────────────────────

    @Test
    void buscarPorEdad_returns200WithResults() {
        when(userPortIn.buscarPorEdad("25")).thenReturn(List.of(buildUser()));

        ResponseEntity<UserResponse> response = userController.buscarPorEdad("25");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().getStatus());
    }

    // ── GET /count ────────────────────────────────────────────────────────────

    @Test
    void contarUsuarios_returns200WithTotal() {
        when(userPortIn.count()).thenReturn(7L);

        ResponseEntity<UserResponse> response = userController.contarUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("SUCCESS", response.getBody().getStatus());
        assertEquals(7L, response.getBody().getData());
    }
}
