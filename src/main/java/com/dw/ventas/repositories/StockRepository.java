package com.dw.ventas.repositories;

import com.dw.ventas.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Integer> {
    Optional<Stock> findByIdProducto(Integer idProducto);
}
