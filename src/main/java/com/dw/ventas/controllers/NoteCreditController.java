package com.dw.ventas.controllers;


import com.dw.ventas.entities.NotaCredito;
import com.dw.ventas.models.NotaCreditoRequest;
import com.dw.ventas.models.NotaCreditoResponse;
import com.dw.ventas.services.NotaCreditoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/notacredito")
public class NoteCreditController {

    private final NotaCreditoService notaCreditoService;

    public NoteCreditController(final NotaCreditoService notaCreditoService) {
        this.notaCreditoService = notaCreditoService;
    }

    @PostMapping("/register")
    public ResponseEntity<NotaCredito>createNotaCredito(@Valid @RequestBody final NotaCreditoRequest request) {
        NotaCredito nuevanota = notaCreditoService.createNotaCredito(request);
        return new ResponseEntity<>(nuevanota, HttpStatus.CREATED);
    }

}
