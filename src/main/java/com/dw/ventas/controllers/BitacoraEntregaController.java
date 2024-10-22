package com.dw.ventas.controllers;

import com.dw.ventas.models.BitacoraEntregaDTO;
import com.dw.ventas.models.EstadoEntregaDTO;
import com.dw.ventas.models.VentaResponse;
import com.dw.ventas.services.BitacoraEntregaService;
import com.dw.ventas.services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/entregas")
public class BitacoraEntregaController {

    @Autowired
    private BitacoraEntregaService bitacoraEntregaService;

    private final VentaService ventaService;

    public BitacoraEntregaController(final VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @GetMapping("/venta/{id}")
    public ResponseEntity<VentaResponse> getVentaDetails(@PathVariable final Integer id) {
        final VentaResponse ventaResponse = ventaService.findVentaById(id);
        return ResponseEntity.ok(ventaResponse);
    }

    @PatchMapping("/{idBitacora}/estado")
    public ResponseEntity<BitacoraEntregaDTO> editarEstadoEntrega(
            @PathVariable Integer idBitacora,
            @RequestBody EstadoEntregaDTO estadoEntregaDTO) {
        String nuevoEstado = estadoEntregaDTO.getEstado().trim();
        BitacoraEntregaDTO entregaActualizada = bitacoraEntregaService.editarEstadoEntrega(idBitacora, nuevoEstado);
        return ResponseEntity.ok(entregaActualizada);
    }


    @DeleteMapping("/{idBitacora}")
    public ResponseEntity<Void> eliminarEntrega(@PathVariable Integer idBitacora) {
        bitacoraEntregaService.eliminarEntrega(idBitacora);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/codigo/{codigoEntrega}")
    public ResponseEntity<BitacoraEntregaDTO> buscarEntregaPorCodigo(@PathVariable String codigoEntrega) {
        BitacoraEntregaDTO entrega = bitacoraEntregaService.buscarEntregaPorCodigo(codigoEntrega);
        return ResponseEntity.ok(entrega);
    }

    @PostMapping
    public ResponseEntity<BitacoraEntregaDTO> crearEntrega(
            @RequestBody BitacoraEntregaDTO bitacoraEntregaDTO,
            @RequestParam Integer idDepartamento,
            @RequestParam Integer idMunicipio) {
        BitacoraEntregaDTO nuevaEntrega = bitacoraEntregaService.crearEntrega(bitacoraEntregaDTO, idDepartamento, idMunicipio);
        return ResponseEntity.ok(nuevaEntrega);
    }

    @GetMapping
    public ResponseEntity<List<BitacoraEntregaDTO>> getAllBitacoras() {
        List<BitacoraEntregaDTO> bitacoras = bitacoraEntregaService.getAllBitacoras();
        return ResponseEntity.ok(bitacoras);
    }

    @GetMapping("/pdf/{idVenta}")
    public ResponseEntity<byte[]> generarPdfEntrega(@PathVariable Integer idVenta) {
        ByteArrayInputStream bis = bitacoraEntregaService.generarComprobantePDF(idVenta);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=comprobante_entrega.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .body(bis.readAllBytes());
    }

    @GetMapping("/rango-fechas")
    public ResponseEntity<List<BitacoraEntregaDTO>> buscarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<BitacoraEntregaDTO> entregas = bitacoraEntregaService.buscarEntregasPorRangoFechas(startDate, endDate);
        return ResponseEntity.ok(entregas);
    }
}
