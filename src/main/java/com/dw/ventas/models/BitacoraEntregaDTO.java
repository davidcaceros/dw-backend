package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BitacoraEntregaDTO {
    private Integer idBitacora;
    private Integer idVenta;
    private String estado;
    private String latitud;
    private String longitud;
    private String codigoEntrega;
}

