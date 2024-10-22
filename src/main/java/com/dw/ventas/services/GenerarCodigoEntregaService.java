package com.dw.ventas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class GenerarCodigoEntregaService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String generarCodigoEntrega(Integer idDepartamento, Integer idMunicipio) {
        String sql = "SELECT generar_codigo_entrega(?, ?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{idDepartamento, idMunicipio}, String.class);
    }
}
