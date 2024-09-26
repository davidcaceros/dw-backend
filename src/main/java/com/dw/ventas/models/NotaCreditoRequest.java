package com.dw.ventas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
public class NotaCreditoRequest {

    @NotNull
    private Integer idventa;

    @NotNull
    private String descripcion;
}
