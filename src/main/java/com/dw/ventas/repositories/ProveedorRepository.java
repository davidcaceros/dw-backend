package com.dw.ventas.repositories;

import com.dw.ventas.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    Optional<Proveedor> findByIdProveedor(Integer idProveedor);
}