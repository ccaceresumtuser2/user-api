package com.empresa.api.infrastructure.web.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Datos para crear o actualizar un libro")
public class BookRequest {

    @NotBlank(message = "El título del libro es obligatorio")
    @Schema(description = "Título del libro", example = "Cien años de soledad")
    private String titulo;

    @NotNull(message = "El año de publicación es obligatorio")
    @Positive(message = "El año de publicación debe ser un número positivo")
    @Schema(description = "Año de publicación", example = "1967")
    private Integer anioPublicacion;

    @Schema(description = "IDs de los autores del libro", example = "[1, 2]")
    private List<Long> authorIds;
}
