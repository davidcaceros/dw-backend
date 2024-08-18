package com.dw.ventas.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"PERSONA\"")
@Data
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "persona_seq")
    @SequenceGenerator(name = "persona_seq", sequenceName = "\"SEQ_PERSONA\"", allocationSize = 1)
    @Column(name = "ID_PERSONA")
    private Integer idPersona;

    @NotBlank
    @Size(max = 25)
    @Column(name = "PRIMER_NOMBRE", nullable = false)
    private String primerNombre;

    @Size(max = 25)
    @Column(name = "SEGUNDO_NOMBRE")
    private String segundoNombre;

    @NotBlank
    @Size(max = 25)
    @Column(name = "PRIMER_APELLIDO", nullable = false)
    private String primerApellido;

    @Size(max = 25)
    @Column(name = "SEGUNDO_APELLIDO")
    private String segundoApellido;

    @Size(max = 25)
    @Column(name = "APELLIDO_CASADA")
    private String apellidoCasada;

    @NotBlank
    @Size(max = 14)
    @Column(name = "DPI", nullable = false)
    private String dpi;

    @Size(max = 25)
    @Column(name = "NIT")
    private String nit;

    @Size(max = 20)
    @Column(name = "PASAPORTE")
    private String pasaporte;

    @NotBlank
    @Size(max = 9)
    @Column(name = "TELEFONO", nullable = false)
    private String telefono;

    @Size(max = 50)
    @Column(name = "CORREO", unique = true)
    private String correo;

    @NotBlank
    @Size(max = 100)
    @Column(name = "DIRECCION", nullable = false)
    private String direccion;

    @NotBlank
    @Size(max = 32)
    @Column(name = "ESTADO", nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "ID_ROL")
    private Rol rol;

    @Column(name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column(name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;
}
