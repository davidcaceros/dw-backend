package com.dw.ventas.models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VisitaResponse {
    private Long id;
    private Long idSolicitud;
    private String latitud;
    private String longitud;
    private String comentario;
    private String fechaHoraInicio;
    private String fechaHoraFin;
}
