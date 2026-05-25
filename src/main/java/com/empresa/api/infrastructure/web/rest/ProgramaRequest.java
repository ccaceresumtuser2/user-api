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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Schema(description = "Datos para crear o actualizar un programa académico")
public class ProgramaRequest {

    @NotBlank(message = "El nombre del programa es obligatorio")
    @Schema(description = "Nombre del programa", example = "Ingeniería de Sistemas")
    private String nombre;

    @NotNull(message = "La duración en semestres es obligatoria")
    @Positive(message = "La duración en semestres debe ser mayor a cero")
    @Schema(description = "Duración en semestres", example = "10")
    private Integer duracionSemestres;

    @NotNull(message = "El ID de la facultad es obligatorio")
    @Schema(description = "ID de la facultad a la que pertenece", example = "1")
    private Long facultadId;
}
