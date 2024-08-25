package com.dw.ventas.models;

import com.dw.ventas.entities.Persona;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UsuarioResponse {
    private Integer idUsuario;
    private Boolean activo;
    private String fechaCreacion;
    private String fechaActualizacion;
    private Persona persona;
}
