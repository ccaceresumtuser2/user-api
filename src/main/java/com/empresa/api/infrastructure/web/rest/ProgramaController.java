package com.empresa.api.infrastructure.web.rest;

import com.empresa.api.application.port.in.ProgramaPortIn;
import com.empresa.api.domain.model.Programa;
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
@RequestMapping("/api/programas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Programas", description = "Operaciones CRUD sobre programas académicos")
@AllArgsConstructor
public class ProgramaController {

    private final ProgramaPortIn programaPortIn;
    private final AppMessages messages;

    @Operation(summary = "Crear programa")
    @PostMapping
    public ResponseEntity<UserResponse> crear(@Valid @RequestBody ProgramaRequest request) {
        log.info("POST /api/programas - Creando programa: {}", request.getNombre());
        Programa programa = Programa.builder()
            .nombre(request.getNombre())
            .duracionSemestres(request.getDuracionSemestres())
            .facultadId(request.getFacultadId())
            .build();
        Programa created = programaPortIn.crear(programa);
        return ResponseEntity.ok(buildResponse(messages.getProgramaCreado(), created));
    }

    @Operation(summary = "Listar todos los programas")
    @GetMapping
    public ResponseEntity<UserResponse> listar() {
        log.info("GET /api/programas - Listando programas");
        return ResponseEntity.ok(buildResponse(messages.getProgramaLista(), programaPortIn.findAll()));
    }

    @Operation(summary = "Obtener programa por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        log.info("GET /api/programas/{} - Buscando programa", id);
        return programaPortIn.findById(id)
            .map(p -> ResponseEntity.ok(buildResponse(messages.getProgramaEncontrado(), p)))
            .orElseGet(() -> ResponseEntity.status(404).body(buildError(
                String.format(messages.getProgramaNoEncontrado(), id))));
    }

    @Operation(summary = "Listar programas por facultad")
    @GetMapping("/facultad/{facultadId}")
    public ResponseEntity<UserResponse> findByFacultad(@PathVariable Long facultadId) {
        log.info("GET /api/programas/facultad/{} - Buscando programas por facultad", facultadId);
        return ResponseEntity.ok(buildResponse(messages.getProgramaListaFacultad(), programaPortIn.findByFacultad(facultadId)));
    }

    @Operation(summary = "Actualizar programa")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody ProgramaRequest request) {
        log.info("PUT /api/programas/{} - Actualizando programa", id);
        Programa programa = Programa.builder()
            .nombre(request.getNombre())
            .duracionSemestres(request.getDuracionSemestres())
            .facultadId(request.getFacultadId())
            .build();
        Programa updated = programaPortIn.actualizar(id, programa);
        return ResponseEntity.ok(buildResponse(messages.getProgramaActualizado(), updated));
    }

    @Operation(summary = "Eliminar programa")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/programas/{} - Eliminando programa", id);
        programaPortIn.eliminar(id);
        return ResponseEntity.ok(buildResponse(messages.getProgramaEliminado(), null));
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
