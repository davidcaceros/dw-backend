package com.dw.ventas.services;

import com.dw.ventas.entities.DetalleVenta;
import com.dw.ventas.entities.Persona;
import com.dw.ventas.entities.Producto;
import com.dw.ventas.entities.Venta;
import com.dw.ventas.exception.impl.BadRequestException;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.*;
import com.dw.ventas.repositories.DetalleVentaRepository;
import com.dw.ventas.repositories.PersonaRepository;
import com.dw.ventas.repositories.ProductoRepository;
import com.dw.ventas.repositories.VentaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Service

public class VentaService {
    private final VentaRepository ventaRepository;
    private final PersonaRepository personaRepository;
    private final ProductoRepository productoRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final StockService stockService;


    public VentaService(final VentaRepository ventaRepository,
                        final PersonaRepository personaRepository,
                        final ProductoRepository productoRepository,
                        final DetalleVentaRepository detalleVentaRepository,
                        final StockService stockService) {
        this.ventaRepository = ventaRepository;
        this.personaRepository = personaRepository;
        this.productoRepository = productoRepository;
        this.detalleVentaRepository = detalleVentaRepository;
        this.stockService = stockService;
    }

    public VentaResponse createVenta(final VentaRequest ventaRequest) {

        if (ventaRequest.getDetalleVenta() == null || ventaRequest.getDetalleVenta().isEmpty()) {
            throw new BadRequestException.Builder()
                    .errorMessageKey("No es posible crear una venta sin productos")
                    .build();
        }

        final Optional<Persona> clienteOptional = personaRepository.findById(ventaRequest.getIdCliente());
        if (clienteOptional.isEmpty()) {
            throw ResourceNotFoundException.builder()
                    .errorMessageKey("No se encontro el cliente con el id proporcionado")
                    .build();
        }

        final Optional<Persona> vendedorOptional = personaRepository.findById(ventaRequest.getIdVendedor());
        if (vendedorOptional.isEmpty()) {
            throw ResourceNotFoundException.builder()
                    .errorMessageKey("No se encontro el vendedor con el id proporcionado")
                    .build();
        }

        final Venta venta = Venta.builder()
                .cliente(clienteOptional.get())
                .vendedor(vendedorOptional.get())
                .tipoVenta(ventaRequest.getTipoVenta())
                .tipoPago(ventaRequest.getTipoPago())
                .estado("CURSO")
                .devolucion(false)
                .fechaCreacion(LocalDateTime.now())
                .fechaActualizacion(LocalDateTime.now())
                .build();

        double totalVenta = 0.0;

        final List<DetalleVenta> detallesVenta = new ArrayList<>();
        final List<StockReductionRequest> stockReductionRequestList = new ArrayList<>();

        for (VentaRequest.DetalleVentaRequest detalleRequest : ventaRequest.getDetalleVenta()) {
            Producto producto = productoRepository.findById(detalleRequest.getIdProducto())
                    .orElseThrow(() -> ResourceNotFoundException.builder()
                            .errorMessageKey("Producto no encontrado con ID: " + detalleRequest.getIdProducto())
                            .build());

            final Double cantidad = detalleRequest.getCantidad();
            double totalDetalle = cantidad * producto.getPrecio();
            totalVenta += totalDetalle;

            final DetalleVenta detalleVenta = DetalleVenta.builder()
                    .producto(producto)
                    .venta(venta)
                    .cantidad(cantidad)
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            detallesVenta.add(detalleVenta);

            //AFECTAR STOCK
            final StockReductionRequest stockReductionRequest = new StockReductionRequest();
            stockReductionRequest.setProductId(producto.getIdProducto());
            stockReductionRequest.setSaleQuantity((int) Double.parseDouble(String.valueOf(cantidad)));
            stockReductionRequestList.add(stockReductionRequest);
        }

        //AFECTAR STOCK
        stockService.reduceStockForMultipleProducts(stockReductionRequestList);

        venta.setTotalVenta(totalVenta);
        final Venta ventaSaved = ventaRepository.save(venta);

        final List<DetalleVenta> detalleVentasSaved = detalleVentaRepository.saveAll(detallesVenta);
        final Persona vendedor = ventaSaved.getVendedor();
        return VentaResponse.builder()
                .idVenta(ventaSaved.getIdVenta())
                .cliente(ventaSaved.getCliente())
                .vendedor(vendedor.getPrimerNombre() + " " + vendedor.getPrimerApellido())
                .tipoVenta(ventaSaved.getTipoVenta())
                .tipoPago(ventaSaved.getTipoPago())
                .estado(ventaSaved.getEstado())
                .devolucion(ventaSaved.getDevolucion())
                .totalVenta(ventaSaved.getTotalVenta())
                .fechaCreacion(ventaSaved.getFechaCreacion().toString())
                .fechaActualizacion(ventaSaved.getFechaActualizacion().toString())
                .detalleVenta(detalleVentasSaved.stream().map(this::detalleVentaResponseBuilder).collect(Collectors.toList()))
                .build();
    }

    public VentaResponse findVentaById(final Integer id) {
        final Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .errorMessageKey("No se encontro la venta con el id proporcionado")
                        .build());
        return ventaResponseBuilder(venta);
    }

    public List<VentaResponse> findAllVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        return ventas.stream()
                .map(this::ventaResponseBuilder)
                .collect(Collectors.toList());
    }

    public VentaResponse updateVenta(final Integer id, final VentaUpdateRequest request) {
        final Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .errorMessageKey("No se encontro la venta con el id proporcionado")
                        .build());

        if (nonNull(request.getDevolucion())) {
            venta.setDevolucion(request.getDevolucion());
        }

        if (nonNull(request.getSaldoFavor())) {
            venta.setSaldoFavor(request.getSaldoFavor());
        }

        if (nonNull(request.getEstado())) {
            venta.setEstado(request.getEstado());
        }

        venta.setFechaActualizacion(LocalDateTime.now());

        final Venta ventaUpdated = ventaRepository.save(venta);

        return ventaResponseBuilder(ventaUpdated);
    }

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
}
