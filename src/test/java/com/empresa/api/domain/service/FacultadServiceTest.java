package com.empresa.api.domain.service;

import com.empresa.api.domain.model.Facultad;
import com.empresa.api.infrastructure.config.AppMessages;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultadServiceTest {

    @Mock
    private AppMessages messages;

    private FacultadService facultadService;

    @BeforeEach
    void setUp() {
        when(messages.getFacultadNombreObligatorio()).thenReturn("El nombre de la facultad es obligatorio");
        when(messages.getFacultadDecanoObligatorio()).thenReturn("El decano es obligatorio");
        when(messages.getFacultadNombreMinimo()).thenReturn("El nombre de la facultad debe tener al menos 3 caracteres");
        when(messages.getFacultadDuplicado()).thenReturn("Ya existe una facultad con el nombre: %s");
        when(messages.getFacultadListaVacia()).thenReturn("No hay facultades registradas");
        facultadService = new FacultadService(messages);
    }

    // ── validar ─────────────────────────────────────────────────────────────

    @Test
    void validar_withValidFacultad_noException() {
        Facultad f = Facultad.builder().nombre("Ingenieria").decano("Dr. Juan").build();
        assertDoesNotThrow(() -> facultadService.validar(f));
    }

    @Test
    void validar_withNullNombre_throws() {
        Facultad f = Facultad.builder().nombre(null).decano("Dr. Juan").build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> facultadService.validar(f));
        assertTrue(ex.getMessage().contains("El nombre de la facultad es obligatorio"));
    }

    @Test
    void validar_withBlankNombre_throws() {
        Facultad f = Facultad.builder().nombre("   ").decano("Dr. Juan").build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> facultadService.validar(f));
        assertTrue(ex.getMessage().contains("El nombre de la facultad es obligatorio"));
    }

    @Test
    void validar_withNombreTooShort_throws() {
        Facultad f = Facultad.builder().nombre("AB").decano("Dr. Juan").build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> facultadService.validar(f));
        assertTrue(ex.getMessage().contains("El nombre de la facultad debe tener al menos 3 caracteres"));
    }

    @Test
    void validar_withNullDecano_throws() {
        Facultad f = Facultad.builder().nombre("Ingenieria").decano(null).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> facultadService.validar(f));
        assertTrue(ex.getMessage().contains("El decano es obligatorio"));
    }

    @Test
    void validar_withBlankDecano_throws() {
        Facultad f = Facultad.builder().nombre("Ingenieria").decano("").build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> facultadService.validar(f));
        assertTrue(ex.getMessage().contains("El decano es obligatorio"));
    }

    @Test
    void validar_withAllFieldsMissing_throwsWithAllErrors() {
        Facultad f = Facultad.builder().nombre(null).decano(null).build();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> facultadService.validar(f));
        assertAll(
                () -> assertTrue(ex.getMessage().contains("El nombre de la facultad es obligatorio")),
                () -> assertTrue(ex.getMessage().contains("El decano es obligatorio"))
        );
    }

    // ── validarDuplicado ─────────────────────────────────────────────────────

    @Test
    void validarDuplicado_whenExists_throwsWithNombre() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> facultadService.validarDuplicado(true, "Ingenieria"));
        assertTrue(ex.getMessage().contains("Ingenieria"));
    }

    @Test
    void validarDuplicado_whenNotExists_noException() {
        assertDoesNotThrow(() -> facultadService.validarDuplicado(false, "Ingenieria"));
    }

    // ── validarNoVacia ───────────────────────────────────────────────────────

    @Test
    void validarNoVacia_withEmptyList_throws() {
        assertThrows(IllegalArgumentException.class,
                () -> facultadService.validarNoVacia(List.of()));
    }

    @Test
    void validarNoVacia_withNonEmptyList_noException() {
        List<Facultad> list = List.of(
                Facultad.builder().id(1L).nombre("Ingenieria").decano("Dr. Juan").build());
        assertDoesNotThrow(() -> facultadService.validarNoVacia(list));
    }
}
