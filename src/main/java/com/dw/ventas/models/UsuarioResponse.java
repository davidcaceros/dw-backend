package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String apellido;
    private String dpi;
    private String email;
    private String telefono;
    private Long rolId;
    private String rolSlug;
    private Boolean enabled;
}
