package com.dw.ventas.services;

import com.dw.ventas.entities.Municipio;
import com.dw.ventas.models.MunicipioDTO;
import com.dw.ventas.repositories.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MunicipioService {

    @Autowired
    private MunicipioRepository municipioRepository;

    public List<MunicipioDTO> getMunicipiosByDepartamentoId(Integer idDepartamento) {
        List<Municipio> municipios = municipioRepository.findByDepartamento_IdDepartamento(idDepartamento);
        return municipios.stream()
                .map(municipio -> new MunicipioDTO(municipio.getIdMunicipio(),
                        municipio.getNombreMunicipio(),
                        municipio.getCodigoMunicipio()))
                .collect(Collectors.toList());
    }
}

