package com.empresa.api.infrastructure.web.rest;

import com.empresa.api.application.port.in.AuthorPortIn;
import com.empresa.api.domain.model.Author;
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
@RequestMapping("/api/authors")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Autores", description = "Operaciones CRUD sobre autores (relación muchos a muchos con libros)")
@AllArgsConstructor
public class AuthorController {

    private final AuthorPortIn authorPortIn;
    private final AppMessages messages;

    @Operation(summary = "Crear autor")
    @PostMapping
    public ResponseEntity<UserResponse> crear(@Valid @RequestBody AuthorRequest request) {
        log.info("POST /api/authors - Creando autor: {}", request.getNombre());
        Author author = Author.builder()
            .nombre(request.getNombre())
            .nacionalidad(request.getNacionalidad())
            .build();
        Author created = authorPortIn.crear(author);
        return ResponseEntity.ok(buildResponse(messages.getAuthorCreado(), created));
    }

    @Operation(summary = "Listar todos los autores")
    @GetMapping
    public ResponseEntity<UserResponse> listar() {
        log.info("GET /api/authors - Listando autores");
        return ResponseEntity.ok(buildResponse(messages.getAuthorLista(), authorPortIn.findAll()));
    }

    @Operation(summary = "Obtener autor por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        log.info("GET /api/authors/{} - Buscando autor", id);
        return authorPortIn.findById(id)
            .map(a -> ResponseEntity.ok(buildResponse(messages.getAuthorEncontrado(), a)))
            .orElseGet(() -> ResponseEntity.status(404).body(buildError(
                String.format(messages.getAuthorNoEncontrado(), id))));
    }

    @Operation(summary = "Actualizar autor")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody AuthorRequest request) {
        log.info("PUT /api/authors/{} - Actualizando autor", id);
        Author author = Author.builder()
            .nombre(request.getNombre())
            .nacionalidad(request.getNacionalidad())
            .build();
        Author updated = authorPortIn.actualizar(id, author);
        return ResponseEntity.ok(buildResponse(messages.getAuthorActualizado(), updated));
    }

    @Operation(summary = "Eliminar autor")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/authors/{} - Eliminando autor", id);
        authorPortIn.eliminar(id);
        return ResponseEntity.ok(buildResponse(messages.getAuthorEliminado(), null));
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
