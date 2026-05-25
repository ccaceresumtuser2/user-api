package com.empresa.api.infrastructure.web.rest;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Respuesta estándar de la API para operaciones de usuario")
public class UserResponse {

    @Schema(description = "Estado de la operación", example = "SUCCESS")
    private String status;

    @Schema(description = "Mensaje descriptivo del resultado", example = "Usuario creado exitosamente")
    private String message;

    @Schema(description = "Datos del usuario retornados por la operación")
    private Object data;

    @Schema(description = "Detalle del error en caso de fallo", example = "El email ya está registrado")
    private String error;

    @Schema(description = "Fecha y hora de la respuesta en ISO 8601", example = "2026-05-24T10:30:00")
    private String timestamp;
}
