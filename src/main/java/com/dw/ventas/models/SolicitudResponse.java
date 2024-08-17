package com.dw.ventas.models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SolicitudResponse {
    private Long id;
    private UsuarioResponse cliente;
    private String tipoServicio;
    private String descripcion;
    private String direccion;
    private String latitud;
    private String longitud;
    private UsuarioResponse tecnico;
    private UsuarioResponse supervisor;
    private String fechaVisita;
    private VisitaResponse visita;
    private String estatus;
    private Boolean enabled;
    private String fechaCreacion;
    private String fechaActualizacion;
}
