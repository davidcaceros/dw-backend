package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "rol")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_seq")
    @SequenceGenerator(name = "rol_seq", sequenceName = "seq_rol", allocationSize = 1)
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "nombre", length = 25)
    private String nombre;

    @Column(name = "slug", length = 25)
    private String slug;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
