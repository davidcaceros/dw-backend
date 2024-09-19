package com.dw.ventas.models;

import com.dw.ventas.entities.Persona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class VentaResponse {

    private Integer idVenta;
    private Persona cliente;
    private String vendedor;
    private String tipoVenta;
    private String tipoPago;
    private String estado;
    private Double totalVenta;
    private String fechaCreacion;
    private String fechaActualizacion;
    private Boolean devolucion;
    private Double saldoFavor;
    private List<DetalleVentaResponse> detalleVenta;
}
