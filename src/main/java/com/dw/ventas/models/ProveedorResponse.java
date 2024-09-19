package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ProveedorResponse {
    private Integer idproveedor;
    private String nombre_prov;
    private String empresa;
    private String correo;
    private String telefono;
}
