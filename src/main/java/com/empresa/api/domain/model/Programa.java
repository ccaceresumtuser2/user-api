package com.empresa.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Programa {
    private Long id;
    private String nombre;
    private Integer duracionSemestres;
    private Long facultadId;
    private String facultadNombre;
}
