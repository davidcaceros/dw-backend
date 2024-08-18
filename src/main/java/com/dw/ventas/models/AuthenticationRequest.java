package com.dw.ventas.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Username is mandatory")
    private String usuario;

    @NotBlank(message = "Password is mandatory")
    private String contrasena;
}
