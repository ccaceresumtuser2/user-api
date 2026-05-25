package com.empresa.api.infrastructure.web.rest;

import com.empresa.api.application.port.in.FacultadPortIn;
import com.empresa.api.domain.model.Facultad;
import com.empresa.api.infrastructure.config.AppMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/api/facultades")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Facultades", description = "Operaciones CRUD sobre facultades")
@AllArgsConstructor
public class FacultadController {

    private final FacultadPortIn facultadPortIn;
    private final AppMessages messages;

    @Operation(summary = "Crear facultad")
    @PostMapping
    public ResponseEntity<UserResponse> crear(@Valid @RequestBody FacultadRequest request) {
        log.info("POST /api/facultades - Creando facultad: {}", request.getNombre());
        Facultad facultad = Facultad.builder()
            .nombre(request.getNombre())
            .decano(request.getDecano())
            .build();
        Facultad created = facultadPortIn.crear(facultad);
        return ResponseEntity.ok(buildResponse(messages.getFacultadCreada(), created));
    }

    @Operation(summary = "Listar todas las facultades")
    @GetMapping
    public ResponseEntity<UserResponse> listar() {
        log.info("GET /api/facultades - Listando facultades");
        return ResponseEntity.ok(buildResponse(messages.getFacultadLista(), facultadPortIn.findAll()));
    }

    @Operation(summary = "Obtener facultad por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        log.info("GET /api/facultades/{} - Buscando facultad", id);
        return facultadPortIn.findById(id)
            .map(f -> ResponseEntity.ok(buildResponse(messages.getFacultadEncontrada(), f)))
            .orElseGet(() -> ResponseEntity.status(404).body(buildError(
                String.format(messages.getFacultadNoEncontrada(), id))));
    }

    @Operation(summary = "Actualizar facultad")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody FacultadRequest request) {
        log.info("PUT /api/facultades/{} - Actualizando facultad", id);
        Facultad facultad = Facultad.builder()
            .nombre(request.getNombre())
            .decano(request.getDecano())
            .build();
        Facultad updated = facultadPortIn.actualizar(id, facultad);
        return ResponseEntity.ok(buildResponse(messages.getFacultadActualizada(), updated));
    }

    @Operation(summary = "Eliminar facultad")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/facultades/{} - Eliminando facultad", id);
        facultadPortIn.eliminar(id);
        return ResponseEntity.ok(buildResponse(messages.getFacultadEliminada(), null));
    }

    private UserResponse buildResponse(String message, Object data) {
        return UserResponse.builder()
            .status("SUCCESS")
            .message(message)
            .data(data)
            .timestamp(LocalDateTime.now().toString())
            .build();
    }

    private UserResponse buildError(String error) {
        return UserResponse.builder()
            .status("ERROR")
            .message(messages.getErrorOperacionFallida())
            .error(error)
            .timestamp(LocalDateTime.now().toString())
            .build();
    }
}
