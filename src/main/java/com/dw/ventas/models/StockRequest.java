package com.dw.ventas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class StockRequest {

    @NotNull
    private Integer idProducto;

    @NotNull
    private Integer existencia;
}
