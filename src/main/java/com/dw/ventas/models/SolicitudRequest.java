package com.dw.ventas.models;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SolicitudRequest {
    private Long id;
    private Long clienteId;
    private String tipoServicio;
    private String descripcion;
    private String direccion;
    private String latitud;
    private String longitud;
    private Long tecnicoId;
    private String fechaVisita;
    private Long visitaId;
    private String estatus;
    private Boolean enabled;
}
