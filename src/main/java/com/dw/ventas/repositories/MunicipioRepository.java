package com.dw.ventas.repositories;

import com.dw.ventas.entities.Municipio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MunicipioRepository extends JpaRepository<Municipio, Integer> {
    List<Municipio> findByDepartamento_IdDepartamento(Integer idDepartamento);
}

