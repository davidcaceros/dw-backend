package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MunicipioDTO {
    private Integer idMunicipio;
    private String nombreMunicipio;
    private String codigoMunicipio;
}
