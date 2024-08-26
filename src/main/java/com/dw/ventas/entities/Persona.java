package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "persona")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona_seq")
    @SequenceGenerator(name = "persona_seq", sequenceName = "seq_persona", allocationSize = 1)
    @Column(name = "id_persona")
    private Integer idPersona;

    @NotBlank
    @Size(max = 25)
    @Column(name = "primer_nombre", nullable = false)
    private String primerNombre;

    @Size(max = 25)
    @Column(name = "segundo_nombre")
    private String segundoNombre;

    @NotBlank
    @Size(max = 25)
    @Column(name = "primer_apellido", nullable = false)
    private String primerApellido;

    @Size(max = 25)
    @Column(name = "segundo_apellido")
    private String segundoApellido;

    @Size(max = 25)
    @Column(name = "apellido_casada")
    private String apellidoCasada;

    @NotBlank
    @Size(max = 14)
    @Column(name = "dpi", nullable = false)
    private String dpi;

    @Size(max = 25)
    @Column(name = "nit")
    private String nit;

    @Size(max = 20)
    @Column(name = "pasaporte")
    private String pasaporte;

    @NotBlank
    @Size(max = 9)
    @Column(name = "telefono", nullable = false)
    private String telefono;

    @Size(max = 50)
    @Column(name = "correo", unique = true, nullable = false)
    private String correo;

    @NotBlank
    @Size(max = 100)
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotBlank
    @Size(max = 32)
    @Column(name = "estado", nullable = false)
    private String estado;

    @Size(max = 20)
    @Column(name = "categoria")
    private String categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol")
    private Rol rol;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
