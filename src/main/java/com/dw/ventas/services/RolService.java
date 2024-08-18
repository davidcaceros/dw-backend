package com.dw.ventas.services;

import com.dw.ventas.entities.Rol;
import com.dw.ventas.models.RolRequest;
import com.dw.ventas.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RolService {
    private final RolRepository rolRepository;

    @Autowired
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Rol createRol(RolRequest request) {
        final LocalDateTime now = LocalDateTime.now();

        final Rol rol = Rol.builder()
                .nombre(request.getNombre())
                .slug(request.getSlug())
                .fechaCreacion(now)
                .build();

        return rolRepository.save(rol);
    }
}