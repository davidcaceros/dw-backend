package com.dw.ventas.services;

import com.dw.ventas.entities.Producto;
import com.dw.ventas.entities.Stock;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.StockRequest;
import com.dw.ventas.models.StockUpdateRequest;
import com.dw.ventas.repositories.ProductoRepository;
import com.dw.ventas.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final ProductoRepository productoRepository;

    public StockService(final StockRepository stockRepository,
                        final ProductoRepository productoRepository) {
        this.stockRepository = stockRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Stock createOrUpdateStock(final StockRequest stockRequest) {
        final Optional<Producto> productoOptional = productoRepository.findById(stockRequest.getIdProducto());
        if (productoOptional.isEmpty()) {
            throw ResourceNotFoundException.builder()
                    .errorMessageKey("Producto no encontrado con el id proporcionado")
                    .build();
        }

        final Optional<Stock> existingStock = stockRepository.findByIdProducto(stockRequest.getIdProducto());
        final LocalDateTime now = LocalDateTime.now();

        if (existingStock.isPresent()) {
            final Stock stock = existingStock.get();
            stock.setExistencia(stock.getExistencia() + stockRequest.getExistencia());
            stock.setFechaActualizacion(now);
            return stockRepository.save(stock);
        } else {
            final Stock stock = Stock.builder()
                    .idProducto(stockRequest.getIdProducto())
                    .existencia(stockRequest.getExistencia())
                    .fechaCreacion(now)
                    .fechaActualizacion(now)
                    .build();

            return stockRepository.save(stock);
        }
    }

    public Optional<Stock> findStockById(final Integer id) {
        return stockRepository.findById(id);
    }

    public List<Stock> findAllStocks() {
        return stockRepository.findAll();
    }

    public Stock updateStock(final Integer id, final StockUpdateRequest request) {
        final Optional<Stock> existingStock = stockRepository.findById(id);

        if (existingStock.isEmpty()) {
            throw ResourceNotFoundException.builder()
                    .errorMessageKey("Stock no encontrado por el id proporcionado.")
                    .build();
        }

        final Stock stock = existingStock.get();
        final LocalDateTime now = LocalDateTime.now();

        if (request.getExistencia() != null) {
            stock.setExistencia(request.getExistencia());
        }

        stock.setFechaActualizacion(now);

        return stockRepository.save(stock);
    }
}
