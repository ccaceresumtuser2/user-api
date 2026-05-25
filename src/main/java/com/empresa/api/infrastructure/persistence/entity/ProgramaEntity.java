package com.empresa.api.infrastructure.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "programas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProgramaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer duracionSemestres;

    // Muchos programas pertenecen a una facultad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "facultad_id", nullable = false)
    private FacultadEntity facultad;

    // Un programa tiene muchas asignaturas
    @OneToMany(
        mappedBy      = "programa",
        cascade       = CascadeType.ALL,
        fetch         = FetchType.LAZY,
        orphanRemoval = true
    )
    @Builder.Default
    private List<AsignaturaEntity> asignaturas = new ArrayList<>();
}
