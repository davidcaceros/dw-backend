package com.dw.ventas.controllers;

import com.dw.ventas.models.BitacoraEntregaDTO;
import com.dw.ventas.services.BitacoraEntregaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class TrackingController {
    @Autowired
    private BitacoraEntregaService bitacoraEntregaService;
    @GetMapping("/tracking/{codigoEntrega}")
    public ResponseEntity<BitacoraEntregaDTO> buscarEntregaPorCodigo(@PathVariable String codigoEntrega) {
        BitacoraEntregaDTO entrega = bitacoraEntregaService.buscarEntregaPorCodigo(codigoEntrega);
        return ResponseEntity.ok(entrega);
    }
}

