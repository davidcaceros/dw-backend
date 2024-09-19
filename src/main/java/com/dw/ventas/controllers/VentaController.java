package com.dw.ventas.controllers;

import com.dw.ventas.models.VentaRequest;
import com.dw.ventas.models.VentaResponse;
import com.dw.ventas.models.VentaUpdateRequest;
import com.dw.ventas.services.VentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/venta")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(final VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping()
    public ResponseEntity<VentaResponse> createVenta(@Valid @RequestBody final VentaRequest request) {
        final VentaResponse ventaResponse = ventaService.createVenta(request);
        return new ResponseEntity<>(ventaResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> getVentaById(@PathVariable final Integer id) {
        final VentaResponse ventaResponse = ventaService.findVentaById(id);
        return ResponseEntity.ok(ventaResponse);
    }

    @GetMapping()
    public ResponseEntity<List<VentaResponse>> getAllVentas() {
        final List<VentaResponse> ventas = ventaService.findAllVentas();
        return ResponseEntity.ok(ventas);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaResponse> updateVenta(@PathVariable final Integer id,
                                                     @Valid @RequestBody final VentaUpdateRequest ventaUpdateRequest) {
        return ResponseEntity.ok(ventaService.updateVenta(id, ventaUpdateRequest));
    }
}
