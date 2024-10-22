package com.dw.ventas.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "municipio")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Municipio {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_municipio")
    @SequenceGenerator(name = "seq_municipio", sequenceName = "seq_municipio", allocationSize = 1)
    private Integer idMunicipio;

    @ManyToOne
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamento departamento;

    @Column(name = "nombre_municipio", nullable = false)
    private String nombreMunicipio;

    @Column(name = "codigo_municipio", nullable = false)
    private String codigoMunicipio;

}
