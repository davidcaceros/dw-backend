package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoDTO {
    private Integer idDepartamento;
    private String nombreDepartamento;
    private String codigoDepartamento;

}

