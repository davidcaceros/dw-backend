package com.dw.ventas.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProductoUpdateRequest {

    @Size(max = 50)
    private String descripcion;

    @Size(max = 50)
    private String ubicacionFisica;

    private Date fechaVencimiento;

    private Boolean activo;

}