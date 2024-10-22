package com.dw.ventas.repositories;

import com.dw.ventas.entities.BitacoraEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

import java.util.Optional;

@Repository
public interface BitacoraEntregaRepository extends JpaRepository<BitacoraEntrega, Integer> {
    Optional<BitacoraEntrega> findByCodigoEntrega(String codigoEntrega);
    Optional<BitacoraEntrega> findByIdVenta(Integer idVenta);

    @Query("SELECT e FROM BitacoraEntrega e WHERE e.fechaCreacion BETWEEN :startDate AND :endDate")
    List<BitacoraEntrega> findByFechaCreacionBetween(@Param("startDate") Timestamp startDate,
                                                     @Param("endDate") Timestamp endDate);
}
