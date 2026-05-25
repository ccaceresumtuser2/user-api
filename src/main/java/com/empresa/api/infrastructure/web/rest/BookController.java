package com.empresa.api.infrastructure.web.rest;

import com.empresa.api.application.port.in.BookPortIn;
import com.empresa.api.domain.model.Book;
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
@RequestMapping("/api/books")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Libros", description = "Operaciones CRUD sobre libros (relación muchos a muchos con autores)")
@AllArgsConstructor
public class BookController {

    private final BookPortIn bookPortIn;
    private final AppMessages messages;

    @Operation(summary = "Crear libro", description = "Crea un libro y opcionalmente lo asocia a uno o más autores")
    @PostMapping
    public ResponseEntity<UserResponse> crear(@Valid @RequestBody BookRequest request) {
        log.info("POST /api/books - Creando libro: {}", request.getTitulo());
        Book book = Book.builder()
            .titulo(request.getTitulo())
            .anioPublicacion(request.getAnioPublicacion())
            .authorIds(request.getAuthorIds())
            .build();
        Book created = bookPortIn.crear(book);
        return ResponseEntity.ok(buildResponse(messages.getBookCreado(), created));
    }

    @Operation(summary = "Listar todos los libros")
    @GetMapping
    public ResponseEntity<UserResponse> listar() {
        log.info("GET /api/books - Listando libros");
        return ResponseEntity.ok(buildResponse(messages.getBookLista(), bookPortIn.findAll()));
    }

    @Operation(summary = "Obtener libro por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        log.info("GET /api/books/{} - Buscando libro", id);
        return bookPortIn.findById(id)
            .map(b -> ResponseEntity.ok(buildResponse(messages.getBookEncontrado(), b)))
            .orElseGet(() -> ResponseEntity.status(404).body(buildError(
                String.format(messages.getBookNoEncontrado(), id))));
    }

    @Operation(summary = "Listar libros por autor")
    @GetMapping("/autor/{authorId}")
    public ResponseEntity<UserResponse> findByAuthor(@PathVariable Long authorId) {
        log.info("GET /api/books/autor/{} - Buscando libros por autor", authorId);
        return ResponseEntity.ok(buildResponse(messages.getBookListaAutor(), bookPortIn.findByAuthor(authorId)));
    }

    @Operation(summary = "Actualizar libro", description = "Actualiza datos del libro y reemplaza la lista de autores")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> actualizar(@PathVariable Long id,
                                                   @Valid @RequestBody BookRequest request) {
        log.info("PUT /api/books/{} - Actualizando libro", id);
        Book book = Book.builder()
            .titulo(request.getTitulo())
            .anioPublicacion(request.getAnioPublicacion())
            .authorIds(request.getAuthorIds())
            .build();
        Book updated = bookPortIn.actualizar(id, book);
        return ResponseEntity.ok(buildResponse(messages.getBookActualizado(), updated));
    }

    @Operation(summary = "Eliminar libro")
    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponse> eliminar(@PathVariable Long id) {
        log.info("DELETE /api/books/{} - Eliminando libro", id);
        bookPortIn.eliminar(id);
        return ResponseEntity.ok(buildResponse(messages.getBookEliminado(), null));
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
