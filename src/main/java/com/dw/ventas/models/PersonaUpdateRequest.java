package com.dw.ventas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PersonaUpdateRequest {

    @Size(max = 25)
    private String primerNombre;

    @Size(max = 25)
    private String primerApellido;

    @Size(max = 9)
    private String telefono;

    @Size(max = 100)
    private String direccion;

    @Size(max = 32)
    private String estado;

    @Size(max = 20)
    private String categoria;
}
