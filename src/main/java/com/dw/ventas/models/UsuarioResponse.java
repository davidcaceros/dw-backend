package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UsuarioResponse {
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
}
