package com.dw.ventas.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class UsuarioDTO {
    private static final String MESSAGE_REQUIERED = "El campo es requerido";

    private String nombre;
    private String apellido;
    private String dpi;


    @NotBlank(message = MESSAGE_REQUIERED)
    @NotNull(message = MESSAGE_REQUIERED)
    @Email(message = "El correo debe ser una dirección de correo electrónico válida")
    private String email;

    private String contrasena;
    private String telefono;
    private Long rolId;
}
