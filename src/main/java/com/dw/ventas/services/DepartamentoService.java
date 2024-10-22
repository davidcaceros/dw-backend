package com.dw.ventas.services;

import com.dw.ventas.entities.Departamento;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.DepartamentoDTO;
import com.dw.ventas.repositories.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartamentoService {

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public List<DepartamentoDTO> getAllDepartamentos() {
        List<Departamento> departamentos = departamentoRepository.findAll();
        return departamentos.stream()
                .map(departamento -> new DepartamentoDTO(departamento.getIdDepartamento(),
                        departamento.getNombreDepartamento(),
                        departamento.getCodigoDepartamento()))
                .collect(Collectors.toList());
    }

    public DepartamentoDTO getDepartamentoById(Integer idDepartamento) {
        Departamento departamento = departamentoRepository.findById(idDepartamento)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("Departamento no encontrado con id: " + idDepartamento)
                        .build());
        return new DepartamentoDTO(departamento.getIdDepartamento(),
                departamento.getNombreDepartamento(),
                departamento.getCodigoDepartamento());
    }

}

