package com.dw.ventas.controllers;

import com.dw.ventas.entities.Stock;
import com.dw.ventas.models.StockReductionRequest;
import com.dw.ventas.models.StockRequest;
import com.dw.ventas.models.StockUpdateRequest;
import com.dw.ventas.services.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/stock")
public class StockController {

    private final StockService stockService;

    public StockController(final StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping()
    public ResponseEntity<Stock> createOrUpdateStock(@Valid @RequestBody final StockRequest request) {
        final Stock stock = stockService.createOrUpdateStock(request);
        return new ResponseEntity<>(stock, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<Stock>> getAllStocks() {
        final List<Stock> stocks = stockService.findAllStocks();
        return ResponseEntity.ok(stocks);
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<Stock> getStockByProductId(@PathVariable final Integer idProducto) {
        final Optional<Stock> stock = stockService.findStockByProductId(idProducto);
        return stock.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/producto/{idProducto}")
    public ResponseEntity<Stock> updateStock(@PathVariable final Integer idProducto,
                                             @Valid @RequestBody final StockUpdateRequest request) {
        final Stock stock = stockService.updateStock(idProducto, request);
        return ResponseEntity.ok(stock);
    }

    @PostMapping("/reduce-stock")
    public ResponseEntity<List<Stock>> reduceStock(@RequestBody List<StockReductionRequest> stockReductionRequests) {
        List<Stock> updatedStocks = stockService.reduceStockForMultipleProducts(stockReductionRequests);
        return ResponseEntity.ok(updatedStocks);
    }
}