package com.dw.ventas.controllers;

import com.dw.ventas.entities.Persona;
import com.dw.ventas.entities.Proveedor;
import com.dw.ventas.models.PersonaRequest;
import com.dw.ventas.models.PersonaUpdateRequest;
import com.dw.ventas.models.ProveedorRequest;
import com.dw.ventas.services.PersonaService;
import com.dw.ventas.services.ProveedorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;
    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping("/register")
    public ResponseEntity<Proveedor> createProveedor(@Valid @RequestBody ProveedorRequest request) {
        Proveedor nuevopoveedor = proveedorService.createProveedor(request);
        return new ResponseEntity<>(nuevopoveedor, HttpStatus.CREATED);
    }

    @GetMapping("/findbyid")
    public ResponseEntity<Proveedor> getProveedorId(@PathVariable Integer id) {
        final Optional<Proveedor> proveedor = proveedorService.findProveedorById(id);
        return proveedor.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Proveedor>> getAllProveedores() {
        final List<Proveedor> proveedores = proveedorService.findAllProveedor();
        return ResponseEntity.ok(proveedores);
    }

    @PutMapping("/update")
    public ResponseEntity<Proveedor> updatePersona(@PathVariable Integer id, @Valid @RequestBody ProveedorRequest request) {
        final Proveedor proveedorActualizada = proveedorService.updateProveedor(id, request);
        return ResponseEntity.ok(proveedorActualizada);
    }



}
