package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class RegisterResponse {
    private Integer idUsuario;
    private String usuario;
}
