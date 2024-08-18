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
public class RolRequest {
    @NotBlank(message = "nombre is mandatory")
    private String nombre;

    @NotBlank(message = "slug is mandatory")
    private String slug;
}
