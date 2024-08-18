package com.dw.ventas.services;

import com.dw.ventas.entities.Persona;
import com.dw.ventas.entities.Rol;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.NuevoUsuarioRequest;
import com.dw.ventas.models.PersonaResponse;
import com.dw.ventas.repositories.PersonaRepository;
import com.dw.ventas.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PersonaService {
    private final PersonaRepository personaRepository;

    private final RolRepository rolRepository;

    @Autowired
    public PersonaService(final PersonaRepository personaRepository,
                          final RolRepository rolRepository) {
        this.personaRepository = personaRepository;
        this.rolRepository = rolRepository;
    }

    public Persona registerPersona(final NuevoUsuarioRequest usuarioRequest){
        Optional<Persona> personaOptional = personaRepository.findByCorreo(usuarioRequest.getCorreo());

        if(personaOptional.isPresent()) {
            return null;
        }

        final Rol rol = rolRepository.getReferenceById(usuarioRequest.getIdRol());
        final LocalDateTime now = LocalDateTime.now();

        final Persona persona = Persona.builder()
                .primerNombre(usuarioRequest.getPrimerNombre())
                .segundoNombre(usuarioRequest.getSegundoNombre())
                .primerApellido(usuarioRequest.getPrimerApellido())
                .segundoApellido(usuarioRequest.getSegundoApellido())
                .apellidoCasada(usuarioRequest.getApellidoCasada())
                .dpi(usuarioRequest.getDpi())
                .nit(usuarioRequest.getNit())
                .pasaporte(usuarioRequest.getPasaporte())
                .telefono(usuarioRequest.getTelefono())
                .correo(usuarioRequest.getCorreo())
                .direccion(usuarioRequest.getDireccion())
                .estado("ACTIVO")
                .rol(rol)
                .fechaCreacion(now)
                .fechaActualizacion(now)
                .build();

        return personaRepository.save(persona);
    }

    public PersonaResponse getPersonaByCorreo(String correo) {
        Optional<Persona> personaOptional = personaRepository.findByCorreo(correo);

        if (personaOptional.isEmpty()) {
            return null;
        }

        final Persona persona = personaOptional.get();

        return PersonaResponse.builder()
                .idPersona(persona.getIdPersona())
                .primerNombre(persona.getPrimerNombre())
                .segundoNombre(persona.getSegundoNombre())
                .primerApellido(persona.getPrimerApellido())
                .segundoApellido(persona.getSegundoApellido())
                .apellidoCasada(persona.getApellidoCasada())
                .dpi(persona.getDpi())
                .nit(persona.getNit())
                .pasaporte(persona.getPasaporte())
                .correo(persona.getCorreo())
                .telefono(persona.getTelefono())
                .direccion(persona.getDireccion())
                .estado(persona.getEstado())
                .idRol(persona.getRol().getIdRol())
                .fechaCreacion(persona.getFechaCreacion().toString())
                .fechaActualizacion(persona.getFechaCreacion().toString())
                .build();
    }
}
