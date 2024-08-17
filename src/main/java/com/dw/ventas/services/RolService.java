package com.dw.ventas.services;

import com.dw.ventas.entities.Rol;
import com.dw.ventas.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {
    private final RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Rol guardarRol(Rol rol) {
        return rolRepository.save(rol);
    }
}