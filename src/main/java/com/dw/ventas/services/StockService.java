package com.dw.ventas.services;

import com.dw.ventas.entities.Producto;
import com.dw.ventas.entities.Stock;
import com.dw.ventas.exception.impl.InsufficientStockException;
import com.dw.ventas.exception.impl.MultipleInsufficientStockException;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.StockReductionRequest;
import com.dw.ventas.models.StockRequest;
import com.dw.ventas.models.StockUpdateRequest;
import com.dw.ventas.repositories.ProductoRepository;
import com.dw.ventas.repositories.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public Stock updateStock(final Integer idProducto, final StockUpdateRequest request) {
        final Optional<Stock> existingStock = stockRepository.findByIdProducto(idProducto);

        if (existingStock.isEmpty()) {
            throw ResourceNotFoundException.builder()
                    .errorMessageKey("Stock no encontrado para el producto con id: " + idProducto)
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

    @Transactional
    public List<Stock> reduceStockForMultipleProducts(final List<StockReductionRequest> stockReductionRequests) {
        List<Stock> updatedStocks = new ArrayList<>();
        List<InsufficientStockException> insufficientStockExceptions = new ArrayList<>();

        for (StockReductionRequest request : stockReductionRequests) {
            Optional<Stock> existingStockOpt = stockRepository.findByIdProducto(request.getProductId());

            if (existingStockOpt.isEmpty()) {
                throw ResourceNotFoundException.builder()
                        .errorMessageKey("Stock no encontrado por el id del producto proporcionado: " + request.getProductId())
                        .build();
            }

            Stock existingStock = existingStockOpt.get();

            if (request.getSaleQuantity() > existingStock.getExistencia()) {
                InsufficientStockException insufficientStockException = InsufficientStockException.builder()
                        .message("El stock es insuficiente para cubrir la venta del producto: " + existingStock.getProducto().getNombre())
                        .errorMessageKey("error.stock.insuficiente")
                        .addAdditionalInformation("existenciaActual", existingStock.getExistencia())
                        .addAdditionalInformation("productId", existingStock.getIdProducto())
                        .productName(existingStock.getProducto().getNombre())
                        .build();

                insufficientStockExceptions.add(insufficientStockException);
            } else {
                existingStock.setExistencia(existingStock.getExistencia() - request.getSaleQuantity());
                existingStock.setFechaActualizacion(LocalDateTime.now());
                updatedStocks.add(existingStock);
            }
        }

        if (!insufficientStockExceptions.isEmpty()) {
            throw new MultipleInsufficientStockException(insufficientStockExceptions);
        }

        return stockRepository.saveAll(updatedStocks);
    }

    public Optional<Stock> findStockByProductId(final Integer idProducto) {
        return stockRepository.findByIdProducto(idProducto);
    }
}
