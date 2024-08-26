package com.dw.ventas.repositories;

import com.dw.ventas.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    Optional<Persona> findByCorreo(String email);

    @Query("SELECT p FROM Persona p " +
            "WHERE UPPER(p.primerNombre) LIKE CONCAT('%', UPPER(:term), '%') " +
            "OR UPPER(p.primerApellido) LIKE CONCAT('%', UPPER(:term), '%') " +
            "OR UPPER(p.nit) LIKE CONCAT('%', UPPER(:term), '%') " +
            "OR UPPER(p.estado) LIKE CONCAT('%', UPPER(:term), '%')")
    List<Persona> findByAnyCriteria(@Param("term") String term);
}