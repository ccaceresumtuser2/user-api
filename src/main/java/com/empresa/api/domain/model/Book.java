package com.empresa.api.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class Book {
    private Long id;
    private String titulo;
    private Integer anioPublicacion;
    private List<Long> authorIds;
    private List<String> authorNombres;
}
