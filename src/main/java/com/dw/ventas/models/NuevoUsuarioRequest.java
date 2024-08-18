package com.dw.ventas.models;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class NuevoUsuarioRequest {
    @NotBlank(message = "Primer nombre es obligatorio")
    @Size(max = 25, message = "El primer nombre no puede exceder los 25 caracteres")
    private String primerNombre;

    @Size(max = 25, message = "El segundo nombre no puede exceder los 25 caracteres")
    private String segundoNombre;

    @NotBlank(message = "Primer apellido es obligatorio")
    @Size(max = 25, message = "El primer apellido no puede exceder los 25 caracteres")
    private String primerApellido;

    @Size(max = 25, message = "El segundo apellido no puede exceder los 25 caracteres")
    private String segundoApellido;

    @Size(max = 25, message = "El apellido casada no puede exceder los 25 caracteres")
    private String apellidoCasada;

    @NotBlank(message = "DPI es obligatorio")
    @Size(max = 14, message = "El DPI no puede exceder los 14 caracteres")
    private String dpi;

    @Size(max = 25, message = "El NIT no puede exceder los 25 caracteres")
    private String nit;

    @Size(max = 20, message = "El pasaporte no puede exceder los 20 caracteres")
    private String pasaporte;

    @NotBlank(message = "Teléfono es obligatorio")
    @Size(max = 9, message = "El teléfono no puede exceder los 9 caracteres")
    private String telefono;

    @Size(max = 50, message = "El correo no puede exceder los 50 caracteres")
    @Email(message = "El correo debe ser una dirección de correo electrónico válida")
    private String correo;

    @NotBlank(message = "Dirección es obligatoria")
    @Size(max = 100, message = "La dirección no puede exceder los 100 caracteres")
    private String direccion;

    private Integer idRol;

    @NotBlank(message = "Contraseña es obligatoria")
    @Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres")
    private String contrasena;
}
