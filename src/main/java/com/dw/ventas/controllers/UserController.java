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

    @GetMapping("/{id}")
    public UsuarioResponse getById(@PathVariable final Integer id) {
        return usuarioService.getUserById(id);
    }

    @GetMapping()
    public List<UsuarioResponse> getAll() {
        return usuarioService.getUsers();
    }

    @PutMapping()
    public UsuarioResponse udpate(@RequestBody final UsuarioRequest usuarioRequest) {
        return null;
    }
}