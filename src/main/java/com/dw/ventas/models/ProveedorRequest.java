package com.dw.ventas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class ProveedorRequest {
    @NotNull
    @Size(max = 50)
    private String nombre_prov;

    @Size(max = 50)
    private String empresa;

    @Size(max = 50)
    private String correo;

    @Size(max = 50)
    private String telefono;


}
