package com.dw.ventas.controllers;

import com.dw.ventas.models.DepartamentoDTO;
import com.dw.ventas.models.MunicipioDTO;
import com.dw.ventas.services.DepartamentoService;
import com.dw.ventas.services.MunicipioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    private DepartamentoService departamentoService;

    @Autowired
    private MunicipioService municipioService;
    @GetMapping("/departamentos")
    public ResponseEntity<List<DepartamentoDTO>> getAllDepartamentos() {
        List<DepartamentoDTO> departamentos = departamentoService.getAllDepartamentos();
        return ResponseEntity.ok(departamentos);
    }
    @GetMapping("/municipios/{idDepartamento}")
    public ResponseEntity<List<MunicipioDTO>> getMunicipiosByDepartamento(@PathVariable Integer idDepartamento) {
        List<MunicipioDTO> municipios = municipioService.getMunicipiosByDepartamentoId(idDepartamento);
        return ResponseEntity.ok(municipios);
    }
}

