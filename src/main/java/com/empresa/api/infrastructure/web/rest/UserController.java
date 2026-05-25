package com.empresa.api.infrastructure.web.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.api.application.port.in.UserPortIn;
import com.empresa.api.domain.model.User;
import com.empresa.api.infrastructure.web.rest.mapper.UserMapper;

/**
 * @author Carlos Jose Caceres Ochoa
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
@AllArgsConstructor
public class UserController {

    private final UserPortIn userPortIn;

    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario creado exitosamente",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Datos del usuario inválidos",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    })
    @PostMapping("/new")
    public ResponseEntity<UserResponse> crearUsuario(@RequestBody UserRequest request) {
        log.info("POST /new - Creando usuario con email: {}", request.getEmail());
        User user = UserMapper.toUser(request);
        String message = userPortIn.createUser(user);
        if (message.startsWith("Error")) {
            log.warn("POST /new - Error al crear usuario: {}", message);
            return ResponseEntity.badRequest().body(UserMapper.toErrorResponse(message));
        }
        log.info("POST /new - Usuario creado exitosamente: {}", request.getEmail());
        return ResponseEntity.ok(UserMapper.toResponse(message, user));
    }

    @Operation(summary = "Listar todos los usuarios", description = "Retorna la lista completa de usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
        content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @GetMapping("/list")
    public ResponseEntity<UserResponse> listarUsuarios() {
        log.info("GET /list - Listando todos los usuarios");
        return ResponseEntity.ok(UserMapper.toListResponse(userPortIn.findAll()));
    }

    @Operation(summary = "Buscar por email", description = "Consulta derivada: findByEmail")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "No encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponse> buscarPorEmail(@PathVariable String email) {
        log.info("GET /email/{} - Buscando usuario por email", email);
        return userPortIn.findByEmail(email)
                .map(user -> {
                    log.info("GET /email/{} - Usuario encontrado", email);
                    return ResponseEntity.ok(UserMapper.toResponse("Usuario encontrado", user));
                })
                .orElseGet(() -> {
                    log.warn("GET /email/{} - Usuario no encontrado", email);
                    return ResponseEntity.status(404).body(UserMapper.toErrorResponse("No existe usuario con email: " + email));
                });
    }

    @Operation(summary = "Buscar por nombre", description = "Consulta derivada: findByNombresContainingIgnoreCase")
    @GetMapping("/buscar/nombre")
    public ResponseEntity<UserResponse> buscarPorNombre(@RequestParam String q) {
        log.info("GET /buscar/nombre?q={} - Buscando por nombre", q);
        return ResponseEntity.ok(UserMapper.toListResponse(userPortIn.buscarPorNombre(q)));
    }

    @Operation(summary = "Buscar por apellido", description = "@Query JPQL: buscarPorApellido")
    @GetMapping("/buscar/apellido")
    public ResponseEntity<UserResponse> buscarPorApellido(@RequestParam String q) {
        log.info("GET /buscar/apellido?q={} - Buscando por apellido", q);
        return ResponseEntity.ok(UserMapper.toListResponse(userPortIn.buscarPorApellido(q)));
    }

    @Operation(summary = "Buscar por edad", description = "@Query nativeQuery: buscarPorEdad")
    @GetMapping("/buscar/edad")
    public ResponseEntity<UserResponse> buscarPorEdad(@RequestParam String q) {
        log.info("GET /buscar/edad?q={} - Buscando por edad", q);
        return ResponseEntity.ok(UserMapper.toListResponse(userPortIn.buscarPorEdad(q)));
    }

    @Operation(summary = "Contar usuarios", description = "Retorna el total de registros en la tabla users")
    @ApiResponse(responseCode = "200", description = "Conteo obtenido exitosamente",
        content = @Content(schema = @Schema(implementation = UserResponse.class)))
    @GetMapping("/count")
    public ResponseEntity<UserResponse> contarUsuarios() {
        log.info("GET /count - Contando usuarios");
        long total = userPortIn.count();
        log.info("GET /count - Total de usuarios: {}", total);
        return ResponseEntity.ok(UserResponse.builder()
                .status("SUCCESS")
                .message("Total de usuarios registrados")
                .data(total)
                .timestamp(java.time.LocalDateTime.now().toString())
                .build());
    }
}
