package com.dw.ventas.repositories;

import com.dw.ventas.entities.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {

    List<Solicitud> findByEnabledTrue();
    List<Solicitud> findByEstatusAndEnabledTrue(String estatus);
    List<Solicitud> findAllByEnabledTrueOrderByFechaCreacionDesc();

}
