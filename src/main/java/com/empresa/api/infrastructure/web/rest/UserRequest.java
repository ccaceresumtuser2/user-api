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
@Schema(description = "Datos del usuario para crear o actualizar")
public class UserRequest {

    @Schema(description = "Nombres del usuario", example = "Carlos José")
    private String nombres;

    @Schema(description = "Apellidos del usuario", example = "Cáceres Ochoa")
    private String apellidos;

    @Schema(description = "Correo electrónico del usuario", example = "usuario@empresa.com")
    private String email;

    @Schema(description = "Edad del usuario", example = "25")
    private String edad;
}
