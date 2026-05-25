package com.empresa.api.infrastructure.web.rest;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Datos para crear o actualizar un autor")
public class AuthorRequest {

    @NotBlank(message = "El nombre del autor es obligatorio")
    @Schema(description = "Nombre completo del autor", example = "Gabriel García Márquez")
    private String nombre;

    @NotBlank(message = "La nacionalidad es obligatoria")
    @Schema(description = "Nacionalidad del autor", example = "Colombiano")
    private String nacionalidad;
}
