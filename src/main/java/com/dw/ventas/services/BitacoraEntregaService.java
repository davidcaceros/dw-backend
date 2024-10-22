package com.dw.ventas.services;

import com.dw.ventas.entities.*;
import com.dw.ventas.exception.impl.BadRequestException;
import com.dw.ventas.exception.impl.InvalidDateRangeException;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.BitacoraEntregaDTO;
import com.dw.ventas.models.DetalleVentaResponse;
import com.dw.ventas.models.VentaResponse;
import com.dw.ventas.repositories.BitacoraEntregaRepository;
import com.dw.ventas.repositories.DetalleVentaRepository;
import com.dw.ventas.repositories.VentaRepository;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BitacoraEntregaService {

    @Autowired
    private BitacoraEntregaRepository bitacoraEntregaRepository;

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    @Autowired
    private GenerarCodigoEntregaService generarCodigoEntregaService;

    private VentaResponse ventaResponseBuilder(final Venta venta) {
        final List<DetalleVenta> detalleVentaList = detalleVentaRepository.findByVentaIdVenta(venta.getIdVenta());
        final Persona vendedor = venta.getVendedor();

        return VentaResponse.builder()
                .idVenta(venta.getIdVenta())
                .cliente(venta.getCliente())
                .vendedor(vendedor.getPrimerNombre() + " " + vendedor.getPrimerApellido())
                .tipoVenta(venta.getTipoVenta())
                .tipoPago(venta.getTipoPago())
                .estado(venta.getEstado())
                .totalVenta(venta.getTotalVenta())
                .devolucion(venta.getDevolucion())
                .saldoFavor(venta.getSaldoFavor())
                .fechaCreacion(venta.getFechaCreacion().toString())
                .fechaActualizacion(venta.getFechaActualizacion().toString())
                .detalleVenta(detalleVentaList.stream().map(this::detalleVentaResponseBuilder).collect(Collectors.toList()))
                .build();
    }


    private DetalleVentaResponse detalleVentaResponseBuilder(final DetalleVenta detalleVenta) {
        final Producto producto = detalleVenta.getProducto();
        return DetalleVentaResponse.builder()
                .idProducto(producto.getIdProducto())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .cantidad(detalleVenta.getCantidad())
                .precio(producto.getPrecio())
                .build();
    }

    public VentaResponse findVentaById(final Integer id) {
        final Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("No se encontro la venta con el id proporcionado")
                        .build());
        return ventaResponseBuilder(venta);
    }
    public BitacoraEntregaDTO crearEntrega(BitacoraEntregaDTO bitacoraEntregaDTO, Integer idDepartamento, Integer idMunicipio) {
        BitacoraEntrega entrega = new BitacoraEntrega();
        entrega.setIdVenta(bitacoraEntregaDTO.getIdVenta());
        entrega.setEstado(bitacoraEntregaDTO.getEstado());
        entrega.setLatitud("10.12345");
        entrega.setLongitud("20.12345");
        entrega.setFechaCreacion(Timestamp.valueOf(LocalDateTime.now()));
        entrega.setCodigoEntrega(generarCodigoEntregaService.generarCodigoEntrega(idDepartamento, idMunicipio));

        BitacoraEntrega savedEntrega = bitacoraEntregaRepository.save(entrega);
        return new BitacoraEntregaDTO(savedEntrega.getIdBitacora(),
                savedEntrega.getIdVenta(),
                savedEntrega.getEstado(),
                savedEntrega.getLatitud(),
                savedEntrega.getLongitud(),
                savedEntrega.getCodigoEntrega());
    }
    public List<BitacoraEntregaDTO> getAllBitacoras() {
        return bitacoraEntregaRepository.findAll().stream()
                .map(entrega -> new BitacoraEntregaDTO(
                        entrega.getIdBitacora(),
                        entrega.getIdVenta(),
                        entrega.getEstado(),
                        entrega.getLatitud(),
                        entrega.getLongitud(),
                        entrega.getCodigoEntrega()))
                .collect(Collectors.toList());
    }

    public BitacoraEntregaDTO editarEstadoEntrega(Integer idBitacora, String nuevoEstado) {
        if (nuevoEstado.length() > 25) {
            throw BadRequestException.builder()
                    .message("El estado no puede tener más de 25 caracteres")
                    .build();
        }

        BitacoraEntrega bitacoraEntrega = bitacoraEntregaRepository.findById(idBitacora)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("Entrega no encontrada con id: " + idBitacora)
                        .build());

        bitacoraEntrega.setEstado(nuevoEstado);
        bitacoraEntrega.setFechaActualizacion(Timestamp.valueOf(LocalDateTime.now()));
        bitacoraEntregaRepository.save(bitacoraEntrega);

        return new BitacoraEntregaDTO(bitacoraEntrega.getIdBitacora(),
                bitacoraEntrega.getIdVenta(),
                bitacoraEntrega.getEstado(),
                bitacoraEntrega.getLatitud(),
                bitacoraEntrega.getLongitud(),
                bitacoraEntrega.getCodigoEntrega());
    }
    public void eliminarEntrega(Integer idBitacora) {
        BitacoraEntrega bitacoraEntrega = bitacoraEntregaRepository.findById(idBitacora)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("Entrega no encontrada con id: " + idBitacora)
                        .build());
        bitacoraEntregaRepository.delete(bitacoraEntrega);
    }
    public BitacoraEntregaDTO buscarEntregaPorCodigo(String codigoEntrega) {
        BitacoraEntrega bitacoraEntrega = bitacoraEntregaRepository.findByCodigoEntrega(codigoEntrega)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("Entrega no encontrada con código: " + codigoEntrega)
                        .build());
        return new BitacoraEntregaDTO(bitacoraEntrega.getIdBitacora(),
                bitacoraEntrega.getIdVenta(),
                bitacoraEntrega.getEstado(),
                bitacoraEntrega.getLatitud(),
                bitacoraEntrega.getLongitud(),
                bitacoraEntrega.getCodigoEntrega());
    }

    public ByteArrayInputStream generarComprobantePDF(Integer idVenta) {
        VentaResponse venta = findVentaById(idVenta);
        BitacoraEntregaDTO entrega = buscarEntregaPorVenta(idVenta);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Paragraph titulo = new Paragraph("Comprobante Entrega")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setBold();
            document.add(titulo);

            Paragraph codigoEntrega = new Paragraph("Código de Entrega: " + entrega.getCodigoEntrega())
                    .setTextAlignment(TextAlignment.RIGHT);
            document.add(codigoEntrega);

            document.add(new Paragraph("Cliente: " + venta.getCliente().getPrimerNombre() + " " + venta.getCliente().getPrimerApellido()));
            document.add(new Paragraph("NIT: " + venta.getCliente().getNit()));
            document.add(new Paragraph("Teléfono: " + venta.getCliente().getTelefono()));
            document.add(new Paragraph("Dirección: " + venta.getCliente().getDireccion()));
            document.add(new Paragraph(" ")); // Espacio en blanco

            float[] columnWidths = {1, 3, 3, 2, 2}; // Definir ancho de columnas
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Nombre").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Descripción").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Precio").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Cantidad").setBold()));

            for (DetalleVentaResponse detalle : venta.getDetalleVenta()) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getIdProducto()))));
                table.addCell(new Cell().add(new Paragraph(detalle.getNombre())));
                table.addCell(new Cell().add(new Paragraph(detalle.getDescripcion())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getPrecio()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getCantidad()))));
            }

            document.add(table);

            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public BitacoraEntregaDTO buscarEntregaPorVenta(Integer idVenta) {
        return bitacoraEntregaRepository.findByIdVenta(idVenta)
                .map(entrega -> new BitacoraEntregaDTO(
                        entrega.getIdBitacora(),
                        entrega.getIdVenta(),
                        entrega.getEstado(),
                        entrega.getLatitud(),
                        entrega.getLongitud(),
                        entrega.getCodigoEntrega()))
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("Entrega no encontrada para la venta con id: " + idVenta)
                        .build());
    }

    public List<BitacoraEntregaDTO> buscarEntregasPorRangoFechas(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw InvalidDateRangeException.builder()
                    .message("La fecha final no puede ser menor a la inicial.")
                    .build();
        }

        if (ChronoUnit.MONTHS.between(startDate, endDate) > 3) {
            throw InvalidDateRangeException.builder()
                    .message("El rango de fechas no puede ser mayor a 3 meses.")
                    .build();
        }

        Timestamp startTimestamp = Timestamp.valueOf(startDate.atStartOfDay());
        Timestamp endTimestamp = Timestamp.valueOf(endDate.atTime(LocalTime.MAX));

        List<BitacoraEntrega> entregas = bitacoraEntregaRepository.findByFechaCreacionBetween(startTimestamp, endTimestamp);

        return entregas.stream()
                .map(entrega -> new BitacoraEntregaDTO(
                        entrega.getIdBitacora(),
                        entrega.getIdVenta(),
                        entrega.getEstado(),
                        entrega.getLatitud(),
                        entrega.getLongitud(),
                        entrega.getCodigoEntrega()))
                .collect(Collectors.toList());
    }
}

