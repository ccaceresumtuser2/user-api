package com.empresa.api.application.usecase;

import com.empresa.api.application.port.out.FacultadPortOut;
import com.empresa.api.domain.model.Facultad;
import com.empresa.api.domain.service.FacultadService;
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
class FacultadUseCaseTest {

    @Mock
    private FacultadService facultadService;

    @Mock
    private FacultadPortOut facultadPortOut;

    @InjectMocks
    private FacultadUseCase facultadUseCase;

    private Facultad buildFacultad() {
        return Facultad.builder().id(1L).nombre("Ingenieria").decano("Dr. Juan").build();
    }

    // ── crear ────────────────────────────────────────────────────────────────

    @Test
    void crear_whenValid_validatesAndPersists() {
        Facultad f = buildFacultad();
        when(facultadPortOut.existsByNombre("Ingenieria")).thenReturn(false);
        when(facultadPortOut.crear(f)).thenReturn(f);

        Facultad result = facultadUseCase.crear(f);

        assertNotNull(result);
        assertEquals("Ingenieria", result.getNombre());
        verify(facultadService).validar(f);
        verify(facultadService).validarDuplicado(false, "Ingenieria");
        verify(facultadPortOut).crear(f);
    }

    @Test
    void crear_whenDuplicate_throwsBeforePersisting() {
        Facultad f = buildFacultad();
        when(facultadPortOut.existsByNombre("Ingenieria")).thenReturn(true);
        doThrow(new IllegalArgumentException("Ya existe la facultad"))
                .when(facultadService).validarDuplicado(true, "Ingenieria");

        assertThrows(IllegalArgumentException.class, () -> facultadUseCase.crear(f));
        verify(facultadPortOut, never()).crear(any());
    }

    @Test
    void crear_whenValidationFails_throwsBeforePersisting() {
        Facultad f = Facultad.builder().nombre(null).decano(null).build();
        doThrow(new IllegalArgumentException("Nombre obligatorio"))
                .when(facultadService).validar(f);

        assertThrows(IllegalArgumentException.class, () -> facultadUseCase.crear(f));
        verify(facultadPortOut, never()).existsByNombre(any());
        verify(facultadPortOut, never()).crear(any());
    }

    // ── findAll ──────────────────────────────────────────────────────────────

    @Test
    void findAll_whenNotEmpty_returnsListAndValidates() {
        List<Facultad> list = List.of(buildFacultad());
        when(facultadPortOut.findAll()).thenReturn(list);

        List<Facultad> result = facultadUseCase.findAll();

        assertEquals(1, result.size());
        verify(facultadService).validarNoVacia(list);
    }

    @Test
    void findAll_whenEmpty_throwsFromService() {
        when(facultadPortOut.findAll()).thenReturn(List.of());
        doThrow(new IllegalArgumentException("No hay facultades"))
                .when(facultadService).validarNoVacia(List.of());

        assertThrows(IllegalArgumentException.class, () -> facultadUseCase.findAll());
    }

    // ── findById ─────────────────────────────────────────────────────────────

    @Test
    void findById_whenExists_returnsOptional() {
        when(facultadPortOut.findById(1L)).thenReturn(Optional.of(buildFacultad()));

        Optional<Facultad> result = facultadUseCase.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void findById_whenNotExists_returnsEmpty() {
        when(facultadPortOut.findById(99L)).thenReturn(Optional.empty());

        Optional<Facultad> result = facultadUseCase.findById(99L);

        assertFalse(result.isPresent());
    }

    // ── actualizar ───────────────────────────────────────────────────────────

    @Test
    void actualizar_whenValid_updatesAndReturns() {
        Facultad f = buildFacultad();
        when(facultadPortOut.existsByNombreAndIdNot("Ingenieria", 1L)).thenReturn(false);
        when(facultadPortOut.actualizar(1L, f)).thenReturn(f);

        Facultad result = facultadUseCase.actualizar(1L, f);

        assertNotNull(result);
        verify(facultadService).validar(f);
        verify(facultadService).validarDuplicado(false, "Ingenieria");
        verify(facultadPortOut).actualizar(1L, f);
    }

    @Test
    void actualizar_whenDuplicateNombre_throwsBeforeUpdating() {
        Facultad f = buildFacultad();
        when(facultadPortOut.existsByNombreAndIdNot("Ingenieria", 1L)).thenReturn(true);
        doThrow(new IllegalArgumentException("Nombre duplicado"))
                .when(facultadService).validarDuplicado(true, "Ingenieria");

        assertThrows(IllegalArgumentException.class, () -> facultadUseCase.actualizar(1L, f));
        verify(facultadPortOut, never()).actualizar(any(), any());
    }

    // ── eliminar ─────────────────────────────────────────────────────────────

    @Test
    void eliminar_delegatesToPortOut() {
        facultadUseCase.eliminar(1L);
        verify(facultadPortOut).eliminar(1L);
    }
}
