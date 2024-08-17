package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@Getter
public class TokenResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String token;
    private final UsuarioResponse usuario;
}
