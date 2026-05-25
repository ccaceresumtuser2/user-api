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
@Schema(description = "Datos para crear o actualizar una facultad")
public class FacultadRequest {

    @NotBlank(message = "El nombre de la facultad es obligatorio")
    @Schema(description = "Nombre de la facultad", example = "Facultad de Ingeniería")
    private String nombre;

    @NotBlank(message = "El nombre del decano es obligatorio")
    @Schema(description = "Nombre del decano", example = "Dr. Juan Pérez")
    private String decano;
}
