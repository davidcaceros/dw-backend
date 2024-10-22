package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "departamento")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_departamento")
    @SequenceGenerator(name = "seq_departamento", sequenceName = "seq_departamento", allocationSize = 1)
    private Integer idDepartamento;

    @Column(name = "nombre_departamento", nullable = false)
    private String nombreDepartamento;

    @Column(name = "codigo_departamento", nullable = false)
    private String codigoDepartamento;
}

