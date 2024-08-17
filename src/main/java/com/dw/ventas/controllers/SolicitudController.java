package com.dw.ventas.controllers;

import com.dw.ventas.services.SolicitudService;
import com.dw.ventas.services.VisitaService;
import com.dw.ventas.models.SolicitudRequest;
import com.dw.ventas.models.SolicitudResponse;
import com.dw.ventas.models.VisitaRequest;
import com.dw.ventas.models.VisitaResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;
    private final VisitaService visitaService;

    public SolicitudController(final SolicitudService solicitudService,
                               final VisitaService visitaService) {
        this.solicitudService = solicitudService;
        this.visitaService = visitaService;
    }

    @PostMapping()
    public SolicitudResponse crearSolicitud(@Valid @RequestBody final SolicitudRequest solicitudRequest) {
        return solicitudService.saveSolicitud(solicitudRequest);
    }

    @PutMapping()
    public SolicitudResponse actualizarSolicitud(@Valid @RequestBody final SolicitudRequest solicitudRequest) {
        return solicitudService.updateSolicitud(solicitudRequest);
    }

    @GetMapping("/{id}")
    public SolicitudResponse traerSolicitud(@PathVariable final Long id) {
        return solicitudService.getSolicitud(id);
    }

    @GetMapping()
    public List<SolicitudResponse> traerSolicitudes(@RequestParam(value = "status", required = false) final String status) {
        return solicitudService.getSolicitudes(status);
    }

    @PostMapping("/visita")
    public VisitaResponse crearVisita(@Valid @RequestBody final VisitaRequest visitaRequest) {
        return visitaService.saveVisita(visitaRequest);
    }

    @PutMapping("/visita")
    public VisitaResponse completarVisita(@Valid @RequestBody final VisitaRequest visitaRequest) {
        return visitaService.updateVisita(visitaRequest);
    }
}
