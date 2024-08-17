package com.dw.ventas.models;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioRequest {
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
