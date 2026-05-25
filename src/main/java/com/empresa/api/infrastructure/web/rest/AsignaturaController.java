package com.empresa.api.infrastructure.web.rest;

import com.empresa.api.application.port.in.AsignaturaPortIn;
import com.empresa.api.domain.model.Asignatura;
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
@RequestMapping("/api/asignaturas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Asignaturas", description = "Operaciones CRUD sobre asignaturas")
@AllArgsConstructor
public class AsignaturaController {

    private final AsignaturaPortIn asignaturaPortIn;
    private final AppMessages messages;

    @Operation(summary = "Crear asignatura")
    @PostMapping
    public ResponseEntity<UserResponse> crear(@Valid @RequestBody AsignaturaRequest request) {
        log.info("POST /api/asignaturas - Creando asignatura: {}", request.getNombre());
        Asignatura asignatura = Asignatura.builder()
            .nombre(request.getNombre())
            .creditos(request.getCreditos())
            .semestre(request.getSemestre())
            .programaId(request.getProgramaId())
            .build();
        Asignatura created = asignaturaPortIn.crear(asignatura);
        return ResponseEntity.ok(buildResponse(messages.getAsignaturaCreada(), created));
    }

    @Operation(summary = "Listar todas las asignaturas")
    @GetMapping
    public ResponseEntity<UserResponse> listar() {
        log.info("GET /api/asignaturas - Listando asignaturas");
        return ResponseEntity.ok(buildResponse(messages.getAsignaturaLista(), asignaturaPortIn.findAll()));
    }

    @Operation(summary = "Obtener asignatura por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        log.info("GET /api/asignaturas/{} - Buscando asignatura", id);
        return asignaturaPortIn.findById(id)
            .map(a -> ResponseEntity.ok(buildResponse(messages.getAsignaturaEncontrada(), a)))
            .orElseGet(() -> ResponseEntity.status(404).body(buildError(
                String.format(messages.getAsignaturaNoEncontrada(), id))));
    }

    @Operation(summary = "Listar asignaturas por programa")
    @GetMapping("/programa/{programaId}")
    public ResponseEntity<UserResponse> findByPrograma(@PathVariable Long programaId) {
        log.info("GET /api/asignaturas/programa/{} - Buscando asignaturas por programa", programaId);
        return ResponseEntity.ok(buildResponse(messages.getAsignaturaListaPrograma(), asignaturaPortIn.findByPrograma(programaId)));
    }

    @Operation(summary = "Actualizar asignatura")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody AsignaturaRequest request) {
        log.info("PUT /api/asignaturas/{} - Actualizando asignatura", id);
        Asignatura asignatura = Asignatura.builder()
            .nombre(request.getNombre())
            .creditos(request.getCreditos())
            .semestre(request.getSemestre())
            .programaId(request.getProgramaId())
            .build();
        Asignatura updated = asignaturaPortIn.actualizar(id, asignatura);
        return ResponseEntity.ok(buildResponse(messages.getAsignaturaActualizada(), updated));
    }

    @Operation(summary = "Eliminar asignatura")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/asignaturas/{} - Eliminando asignatura", id);
        asignaturaPortIn.eliminar(id);
        return ResponseEntity.ok(buildResponse(messages.getAsignaturaEliminada(), null));
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
