package com.dw.ventas.models;

import lombok.Data;
import java.util.List;
import javax.validation.constraints.NotNull;

@Data
public class VentaRequest {

    @NotNull
    private Integer idCliente;

    @NotNull
    private Integer idVendedor;

    @NotNull
    private String tipoVenta;

    @NotNull
    private String tipoPago;

    @NotNull
    private List<DetalleVentaRequest> detalleVenta;

    @Data
    public static class DetalleVentaRequest {

        @NotNull
        private Integer idProducto;

        @NotNull
        private Double cantidad;
    }
}
