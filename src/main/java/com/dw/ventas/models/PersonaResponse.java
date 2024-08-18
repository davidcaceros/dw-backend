package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@Getter
public class PersonaResponse {
    private Integer idPersona;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String apellidoCasada;
    private String dpi;
    private String nit;
    private String pasaporte;
    private String correo;
    private String telefono;
    private String direccion;
    private String estado;
    private Integer idRol;
    private String fechaCreacion;
    private String fechaActualizacion;
}
