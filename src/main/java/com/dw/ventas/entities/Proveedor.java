package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "proveedor")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_proveedor")
    @SequenceGenerator(name = "seq_proveedor", sequenceName = "seq_proveedor", allocationSize = 1)
    @Column(name = "id_proveedor")
    private Integer idProveedor;

    @Size(max = 50)
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Size(max = 50)
    @Column(name = "empresa", nullable = true, length = 50)
    private String empresa;

    @Size(max = 50)
    @Column(name = "correo", nullable = true, length = 50)
    private String correo;

    @Size(max = 50)
    @Column(name = "telefono", nullable = true, length = 50)
    private String telefono;

}