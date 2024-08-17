package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationReq {
    private static final long serialVersionUID = 1L;
    private String usuario;
    private String clave;
}
