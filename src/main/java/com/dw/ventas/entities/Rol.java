package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"ROL\"")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rol_seq")
    @SequenceGenerator(name = "rol_seq", sequenceName = "SEQ_ROL", allocationSize = 1)
    @Column(name = "ID_ROL")
    private Integer idRol;

    @Column(name = "NOMBRE", length = 25)
    private String nombre;

    @Column(name = "SLUG", length = 25)
    private String slug;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;
}
