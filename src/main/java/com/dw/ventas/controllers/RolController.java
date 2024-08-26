package com.dw.ventas.controllers;

import com.dw.ventas.entities.Rol;
import com.dw.ventas.models.RolRequest;
import com.dw.ventas.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {
    private final RolService rolService;

    @Autowired
    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @PostMapping
    public Rol createRol(@Valid @RequestBody RolRequest rol) {
        return rolService.createRol(rol);
    }

    @GetMapping
    List<Rol> getAllRoles() {
        return rolService.findAllRoles();
    }
}