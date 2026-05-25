package com.empresa.api.domain.service;

import com.empresa.api.domain.model.User;
import com.empresa.api.infrastructure.config.AppMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AppMessages mockMessages;

    private UserService userService;

    @BeforeEach
    void setUp() {
        when(mockMessages.getUserDatosNulos()).thenReturn("Error: User data is null");
        when(mockMessages.getUserValidado()).thenReturn("Usuario Validado con Exito");
        when(mockMessages.getUserCreado()).thenReturn("User created successfully: %s");
        when(mockMessages.getUserErrorValidacion()).thenReturn("Error al Validar Usuario: %s");
        when(mockMessages.getUserEmailObligatorio()).thenReturn("Email is required");
        when(mockMessages.getUserNombresObligatorio()).thenReturn("First name is required");
        when(mockMessages.getUserApellidosObligatorio()).thenReturn("Last name is required");
        when(mockMessages.getUserEdadObligatorio()).thenReturn("Age is required");
        userService = new UserService(mockMessages);
    }

    // ── createUser ──────────────────────────────────────────────────────────

    @Test
    void createUser_withNullUser_returnsError() {
        String result = userService.createUser(null);
        assertEquals("Error: User data is null", result);
    }

    @Test
    void createUser_withValidUser_returnsSuccess() {
        User user = User.builder()
                .nombres("Carlos")
                .apellidos("Caceres")
                .email("carlos@example.com")
                .edad("25")
                .build();

        String result = userService.createUser(user);

        assertTrue(result.startsWith("User created successfully:"),
                "Expected success message but got: " + result);
    }

    @Test
    void createUser_withInvalidUser_returnsValidationError() {
        User user = User.builder()
                .nombres("Carlos")
                .build();

        String result = userService.createUser(user);

        assertTrue(result.startsWith("Error al Validar Usuario:"),
                "Expected validation error but got: " + result);
    }

    // ── validarUsuario ───────────────────────────────────────────────────────

    @Test
    void validarUsuario_withAllFieldsValid_returnsSuccess() {
        User user = User.builder()
                .nombres("Carlos")
                .apellidos("Caceres")
                .email("carlos@example.com")
                .edad("25")
                .build();

        String result = userService.validarUsuario(user);

        assertEquals("Usuario Validado con Exito", result);
    }

    @Test
    void validarUsuario_withNullEmail_returnsEmailError() {
        User user = User.builder()
                .nombres("Carlos")
                .apellidos("Caceres")
                .email(null)
                .edad("25")
                .build();

        String result = userService.validarUsuario(user);

        assertTrue(result.contains("Email is required"), "Expected email error but got: " + result);
    }

    @Test
    void validarUsuario_withEmptyEmail_returnsEmailError() {
        User user = User.builder()
                .nombres("Carlos")
                .apellidos("Caceres")
                .email("")
                .edad("25")
                .build();

        String result = userService.validarUsuario(user);

        assertTrue(result.contains("Email is required"), "Expected email error but got: " + result);
    }

    @Test
    void validarUsuario_withNullNombres_returnsFirstNameError() {
        User user = User.builder()
                .nombres(null)
                .apellidos("Caceres")
                .email("carlos@example.com")
                .edad("25")
                .build();

        String result = userService.validarUsuario(user);

        assertTrue(result.contains("First name is required"), "Expected first name error but got: " + result);
    }

    @Test
    void validarUsuario_withEmptyNombres_returnsFirstNameError() {
        User user = User.builder()
                .nombres("")
                .apellidos("Caceres")
                .email("carlos@example.com")
                .edad("25")
                .build();

        String result = userService.validarUsuario(user);

        assertTrue(result.contains("First name is required"), "Expected first name error but got: " + result);
    }

    @Test
    void validarUsuario_withNullApellidos_returnsLastNameError() {
        User user = User.builder()
                .nombres("Carlos")
                .apellidos(null)
                .email("carlos@example.com")
                .edad("25")
                .build();

        String result = userService.validarUsuario(user);

        assertTrue(result.contains("Last name is required"), "Expected last name error but got: " + result);
    }

    @Test
    void validarUsuario_withEmptyApellidos_returnsLastNameError() {
        User user = User.builder()
                .nombres("Carlos")
                .apellidos("")
                .email("carlos@example.com")
                .edad("25")
                .build();

        String result = userService.validarUsuario(user);

        assertTrue(result.contains("Last name is required"), "Expected last name error but got: " + result);
    }

    @Test
    void validarUsuario_withNullEdad_returnsAgeError() {
        User user = User.builder()
                .nombres("Carlos")
                .apellidos("Caceres")
                .email("carlos@example.com")
                .edad(null)
                .build();

        String result = userService.validarUsuario(user);

        assertTrue(result.contains("Age is required"), "Expected age error but got: " + result);
    }

    @Test
    void validarUsuario_withEmptyEdad_returnsAgeError() {
        User user = User.builder()
                .nombres("Carlos")
                .apellidos("Caceres")
                .email("carlos@example.com")
                .edad("")
                .build();

        String result = userService.validarUsuario(user);

        assertTrue(result.contains("Age is required"), "Expected age error but got: " + result);
    }

    @Test
    void validarUsuario_withAllFieldsMissing_returnsAllErrors() {
        User user = new User();

        String result = userService.validarUsuario(user);

        assertAll(
                () -> assertTrue(result.contains("Email is required")),
                () -> assertTrue(result.contains("First name is required")),
                () -> assertTrue(result.contains("Last name is required")),
                () -> assertTrue(result.contains("Age is required"))
        );
    }
}
