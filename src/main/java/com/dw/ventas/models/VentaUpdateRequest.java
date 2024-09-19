package com.dw.ventas.models;

import lombok.Data;

@Data
public class VentaUpdateRequest {
    private String estado;
    private Boolean devolucion;
    private Double saldoFavor;
}
