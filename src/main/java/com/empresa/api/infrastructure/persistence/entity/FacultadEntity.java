package com.empresa.api.infrastructure.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "facultades")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class FacultadEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private String decano;

    // Una facultad tiene muchos programas
    @OneToMany(
        mappedBy      = "facultad",
        cascade       = CascadeType.ALL,
        fetch         = FetchType.LAZY,
        orphanRemoval = true
    )
    @Builder.Default
    private List<ProgramaEntity> programas = new ArrayList<>();
}
