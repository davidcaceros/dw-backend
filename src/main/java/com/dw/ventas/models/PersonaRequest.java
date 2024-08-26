package com.dw.ventas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PersonaRequest {

    @NotBlank
    @Size(max = 25)
    private String primerNombre;

    @Size(max = 25)
    private String segundoNombre;

    @NotBlank
    @Size(max = 25)
    private String primerApellido;

    @Size(max = 25)
    private String segundoApellido;

    @Size(max = 25)
    private String apellidoCasada;

    @NotBlank
    @Size(max = 14)
    private String dpi;

    @Size(max = 25)
    private String nit;

    @Size(max = 20)
    private String pasaporte;

    @NotBlank
    @Size(max = 9)
    private String telefono;

    @NotBlank
    @Email
    @Size(max = 50)
    private String correo;

    @NotBlank
    @Size(max = 100)
    private String direccion;

    @NotBlank
    @Size(max = 32)
    private String estado;

    @NotNull
    private Integer idRol;

    @Size(max = 20)
    private String categoria;
}
