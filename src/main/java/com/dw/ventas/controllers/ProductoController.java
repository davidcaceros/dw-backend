package com.dw.ventas.controllers;
import com.dw.ventas.entities.Producto;
import com.dw.ventas.models.ProductoRequest;
import com.dw.ventas.models.ProductoUpdateRequest;
import com.dw.ventas.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(final ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping()
    public ResponseEntity<Producto> createProducto(@Valid @RequestBody final ProductoRequest request) {
        final Producto producto = productoService.createProducto(request);
        return new ResponseEntity<>(producto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable final Integer id) {
        final Optional<Producto> producto = productoService.findProductoById(id);
        return producto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public ResponseEntity<List<Producto>> getAllProductos() {
        final List<Producto> productos = productoService.findAllProductos();
        return ResponseEntity.ok(productos);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> getProductosByAnyCriteria(@RequestParam final String term) {
        final List<Producto> productos = productoService.findProductosByAnyCriteria(term);
        return ResponseEntity.ok(productos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable final Integer id,
                                                   @Valid @RequestBody final ProductoUpdateRequest request) {
        final Producto producto = productoService.updateProducto(id, request);
        return ResponseEntity.ok(producto);
    }
}