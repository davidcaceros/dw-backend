package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class DetalleVentaResponse {
    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Double cantidad;
}
