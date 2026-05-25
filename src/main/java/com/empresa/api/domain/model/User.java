package com.empresa.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Carlos Jose Caceres Ochoa
 * Clase de dominio que representa a un usuario en el sistema.
 * Contiene atributos y métodos relacionados con la información y comportamiento de un usuario.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter 
@Builder
@ToString
public class User {
    private Long id;
    private String nombres;
    private String apellidos;
    private String email;
    private String edad;
}
