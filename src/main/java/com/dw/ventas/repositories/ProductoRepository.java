package com.dw.ventas.repositories;

import com.dw.ventas.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Optional<Producto> findByNombre(String nombre);

    @Query("SELECT p FROM Producto p " +
            "WHERE UPPER(p.nombre) LIKE CONCAT('%', UPPER(:term), '%') " +
            "OR UPPER(p.descripcion) LIKE CONCAT('%', UPPER(:term), '%') " +
            "OR TO_CHAR(p.fechaVencimiento, 'YYYY-MM-DD') LIKE CONCAT('%', :term, '%')")
    List<Producto> findByAnyCriteria(@Param("term") String term);
}