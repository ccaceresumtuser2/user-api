package com.empresa.api.infrastructure.web.rest;

import com.empresa.api.application.port.in.PostPortIn;
import com.empresa.api.domain.model.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
@Tag(name = "Posts", description = "Consumo de JSONPlaceholder - /posts")
public class PostController {

    private final PostPortIn postPortIn;

    @Operation(summary = "Listar todos los posts", description = "Obtiene todos los posts desde JSONPlaceholder")
    @ApiResponse(responseCode = "200", description = "Posts obtenidos exitosamente")
    @GetMapping("/list")
    public ResponseEntity<UserResponse> listarPosts() {
        log.info("GET /api/posts/list");
        List<Post> posts = postPortIn.findAll();
        return ResponseEntity.ok(UserResponse.builder()
                .status("SUCCESS")
                .message("Posts obtenidos de JSONPlaceholder")
                .data(posts)
                .timestamp(LocalDateTime.now().toString())
                .build());
    }

    @Operation(summary = "Buscar post por ID", description = "Obtiene un post por su ID desde JSONPlaceholder")
    @ApiResponse(responseCode = "200", description = "Post encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> buscarPorId(@PathVariable Long id) {
        log.info("GET /api/posts/{}", id);
        Post post = postPortIn.findById(id);
        return ResponseEntity.ok(UserResponse.builder()
                .status("SUCCESS")
                .message("Post encontrado")
                .data(post)
                .timestamp(LocalDateTime.now().toString())
                .build());
    }
}
