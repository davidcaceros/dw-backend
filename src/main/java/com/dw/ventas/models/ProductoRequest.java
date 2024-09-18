package com.dw.ventas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProductoRequest {
    @NotNull
    @Size(max = 50)
    private String nombre;

    @Size(max = 50)
    private String descripcion;

    @Size(max = 50)
    private String ubicacionFisica;

    private Integer existenciaMinima;

    @NotNull
    private Integer codigoProveedor;

    private Date fechaVencimiento;}