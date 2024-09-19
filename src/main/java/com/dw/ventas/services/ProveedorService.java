package com.dw.ventas.services;

import com.dw.ventas.entities.Persona;
import com.dw.ventas.entities.Proveedor;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.PersonaUpdateRequest;
import com.dw.ventas.models.ProveedorRequest;
import com.dw.ventas.repositories.PersonaRepository;
import com.dw.ventas.repositories.ProveedorRepository;
import com.dw.ventas.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Autowired
    public ProveedorService(final ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }


    public Proveedor createProveedor(final ProveedorRequest prov){
        final Proveedor proveedor = Proveedor.builder()
                .nombre(prov.getNombre_prov())
                .empresa(prov.getEmpresa())
                .correo(prov.getCorreo())
                .telefono(prov.getTelefono())
                .build();
     final Proveedor proveedorsave = proveedorRepository.save(proveedor);
     return proveedorsave;
    }

    public Optional<Proveedor> findProveedorById(final Integer idproveedor) {
        return proveedorRepository.findById(idproveedor);
    }

    public List<Proveedor> findAllProveedor() {
        return proveedorRepository.findAll();
    }

    public Proveedor updateProveedor(final Integer id, final ProveedorRequest request) {
        final Optional<Proveedor> existingProveedorOpt = proveedorRepository.findById(id);

        if (existingProveedorOpt.isEmpty()) {
            throw ResourceNotFoundException.builder()
                    .errorMessageKey("No se encontro el proveedor por el id proporcionado.")
                    .build();
        }
        final Proveedor existingProveedor = existingProveedorOpt.get();

        if (request.getNombre_prov() != null) {
            existingProveedor.setNombre(request.getNombre_prov());
        }

        if (request.getEmpresa() != null) {
            existingProveedor.setEmpresa(request.getEmpresa());
        }

        if (request.getTelefono() != null) {
            existingProveedor.setTelefono(request.getTelefono());
        }

        if (request.getCorreo() != null) {
            existingProveedor.setCorreo(request.getCorreo());
        }

        return proveedorRepository.save(existingProveedor);
    }


}
