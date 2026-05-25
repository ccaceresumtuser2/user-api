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
@Schema(description = "Datos para crear o actualizar una asignatura")
public class AsignaturaRequest {

    @NotBlank(message = "El nombre de la asignatura es obligatorio")
    @Schema(description = "Nombre de la asignatura", example = "Cálculo Diferencial")
    private String nombre;

    @NotNull(message = "Los créditos son obligatorios")
    @Positive(message = "Los créditos deben ser mayor a cero")
    @Schema(description = "Número de créditos", example = "3")
    private Integer creditos;

    @NotNull(message = "El semestre es obligatorio")
    @Positive(message = "El semestre debe ser mayor a cero")
    @Schema(description = "Semestre en que se dicta", example = "1")
    private Integer semestre;

    @NotNull(message = "El ID del programa es obligatorio")
    @Schema(description = "ID del programa al que pertenece", example = "1")
    private Long programaId;
}
