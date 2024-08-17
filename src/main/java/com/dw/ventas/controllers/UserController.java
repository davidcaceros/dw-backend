package com.dw.ventas.controllers;


import com.dw.ventas.services.UsuarioService;
import com.dw.ventas.models.UsuarioRequest;
import com.dw.ventas.models.UsuarioResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UsuarioService usuarioService;
    public UserController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    @GetMapping()
    public List<UsuarioResponse> getUsuarios(@RequestParam(value = "slug", required = false) final String slug) {
        return usuarioService.getUsers(slug);
    }

    @GetMapping("/{id}")
    public UsuarioResponse getUsuario(@PathVariable final Long id) {
        return usuarioService.getUserById(id);
    }

    @PutMapping()
    public UsuarioResponse updateUsuario(@RequestBody final UsuarioRequest usuarioRequest) {
        return usuarioService.updateUser(usuarioRequest);
    }
}