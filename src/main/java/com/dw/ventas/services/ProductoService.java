package com.dw.ventas.services;

import com.dw.ventas.entities.Producto;
import com.dw.ventas.entities.Proveedor;
import com.dw.ventas.exception.impl.BadRequestException;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.ProductoRequest;
import com.dw.ventas.models.ProductoUpdateRequest;
import com.dw.ventas.repositories.ProductoRepository;
import com.dw.ventas.repositories.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    private final ProveedorRepository proveedorRepository;

    public ProductoService(final ProductoRepository productoRepository,
                           final ProveedorRepository proveedorRepository) {
        this.productoRepository = productoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional
    public Producto createProducto(final ProductoRequest productoRequest) {
        nombreProductoVerification(productoRequest.getNombre());

        final Optional<Proveedor> proveedorOptional = proveedorRepository.findById(productoRequest.getCodigoProveedor());
        if (proveedorOptional.isEmpty()) {
            throw ResourceNotFoundException.builder()
                    .errorMessageKey("No se encontro el proveedor con el id proporcionado")
                    .build();
        }

        final LocalDateTime localDateTime = LocalDateTime.now();

        final Producto producto = Producto.builder()
                .nombre(productoRequest.getNombre())
                .descripcion(productoRequest.getDescripcion())
                .ubicacionFisica(productoRequest.getUbicacionFisica())
                .existenciaMinima(productoRequest.getExistenciaMinima())
                .precio(productoRequest.getPrecio())
                .codigoProveedor(proveedorOptional.get().getIdProveedor())
                .proveedor(proveedorOptional.get())
                .fechaVencimiento(productoRequest.getFechaVencimiento())
                .activo(true)
                .fechaCreacion(localDateTime)
                .fechaActualizacion(localDateTime)
                .build();

        return productoRepository.save(producto);
    }

    public Optional<Producto> findProductoById(final Integer idProducto) {
        return productoRepository.findById(idProducto);
    }

    public List<Producto> findAllProductos() {
        return productoRepository.findAll();
    }

    public List<Producto> findProductosByAnyCriteria(final String term) {
        return productoRepository.findByAnyCriteria(term);
    }

    public Producto updateProducto(final Integer id, final ProductoUpdateRequest request) {
        final Optional<Producto> existingProducto = productoRepository.findById(id);

        if (existingProducto.isEmpty()) {
            throw ResourceNotFoundException.builder()
                    .errorMessageKey("No se encontro el producto por el id proporcionado.")
                    .build();
        }

        final LocalDateTime localDateTime = LocalDateTime.now();

        final Producto producto = existingProducto.get();

        if (nonNull(request.getDescripcion())) {
            producto.setDescripcion(request.getDescripcion());
        }

        if (nonNull(request.getUbicacionFisica())) {
            producto.setUbicacionFisica(request.getUbicacionFisica());
        }

        if (nonNull(request.getFechaVencimiento())) {
            producto.setFechaVencimiento(request.getFechaVencimiento());
        }

        if (nonNull(request.getActivo())) {
            producto.setActivo(request.getActivo());
        }

        producto.setFechaActualizacion(localDateTime);

        return productoRepository.save(producto);
    }

    private void nombreProductoVerification(final String nombre) {
        final Optional<Producto> productoOptional = productoRepository.findByNombre(nombre);

        if (productoOptional.isPresent()) {
            throw new BadRequestException.Builder()
                    .errorMessageKey("Ya se encuentra un producto registrado con el nombre: " + nombre)
                    .build();
        }
    }

}