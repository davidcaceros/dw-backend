package com.dw.ventas.services;

import com.dw.ventas.entities.Persona;
import com.dw.ventas.entities.Rol;
import com.dw.ventas.exception.impl.BadRequestException;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.PersonaRequest;
import com.dw.ventas.models.PersonaUpdateRequest;
import com.dw.ventas.models.RegisterRequest;
import com.dw.ventas.models.PersonaResponse;
import com.dw.ventas.repositories.PersonaRepository;
import com.dw.ventas.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
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

    @Transactional
    public Persona registerPersonaForUser(final RegisterRequest usuarioRequest) {

        emailVerification(usuarioRequest.getCorreo());

        final Rol rol = getRol(usuarioRequest.getIdRol());

        final LocalDateTime localDateTime = LocalDateTime.now();

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
                .estado(usuarioRequest.getEstado() != null ? usuarioRequest.getEstado() : "ACTIVO")
                .categoria(usuarioRequest.getCategoria())
                .rol(rol)
                .fechaCreacion(localDateTime)
                .fechaActualizacion(localDateTime)
                .build();

        Persona personaSaved = personaRepository.save(persona);

        return personaSaved;
    }

    public Persona createPersona(final PersonaRequest request) {
        emailVerification(request.getCorreo());

        final Rol rol = getRol(request.getIdRol());

        final LocalDateTime localDateTime = LocalDateTime.now();

        final Persona persona = Persona.builder()
                .primerNombre(request.getPrimerNombre())
                .segundoNombre(request.getSegundoNombre())
                .primerApellido(request.getPrimerApellido())
                .segundoApellido(request.getSegundoApellido())
                .apellidoCasada(request.getApellidoCasada())
                .dpi(request.getDpi())
                .nit(request.getNit())
                .pasaporte(request.getPasaporte())
                .telefono(request.getTelefono())
                .correo(request.getCorreo())
                .direccion(request.getDireccion())
                .estado(request.getEstado())
                .rol(rol)
                .categoria(request.getCategoria())
                .fechaCreacion(localDateTime)
                .fechaActualizacion(localDateTime)
                .build();

        final Persona personaSaved = personaRepository.save(persona);

        return personaSaved;
    }

    public PersonaResponse getPersonaByCorreo(final String correo) {
        final Optional<Persona> personaOptional = personaRepository.findByCorreo(correo);

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

    public Optional<Persona> findPersonaById(final Integer idPersona) {
        return personaRepository.findById(idPersona);
    }

    public List<Persona> findAllPersonas() {
        return personaRepository.findAll();
    }

    public List<Persona> findPersonasByAnyCriteria(final String term) {
        return personaRepository.findByAnyCriteria(term);
    }

    public Persona updatePersona(final Integer id, final PersonaUpdateRequest request) {
        final Optional<Persona> existingPersonaOpt = personaRepository.findById(id);

        if (existingPersonaOpt.isEmpty()) {
            throw ResourceNotFoundException.builder()
                    .errorMessageKey("No se encontro el usuario por el id proporcionado.")
                    .build();
        }

        final Persona existingPersona = existingPersonaOpt.get();

        if (request.getPrimerNombre() != null) {
            existingPersona.setPrimerNombre(request.getPrimerNombre());
        }

        if (request.getPrimerApellido() != null) {
            existingPersona.setPrimerApellido(request.getPrimerApellido());
        }

        if (request.getTelefono() != null) {
            existingPersona.setTelefono(request.getTelefono());
        }

        if (request.getDireccion() != null) {
            existingPersona.setDireccion(request.getDireccion());
        }

        if (request.getEstado() != null) {
            existingPersona.setEstado(request.getEstado());
        }

        if (request.getCategoria() != null) {
            existingPersona.setCategoria(request.getCategoria());
        }

        return personaRepository.save(existingPersona);
    }

    private void emailVerification(final String correo) {
        final Optional<Persona> personaOptional = personaRepository.findByCorreo(correo);

        if (personaOptional.isPresent()) {
            throw new BadRequestException.Builder()
                    .errorMessageKey("Ya se encuentra un usuario registrado con el correo: " + correo)
                    .build();
        }
    }

    private Rol getRol(final Integer idRol) {
        final Optional<Rol> rolOptional = rolRepository.findByIdRol(idRol);

        if (rolOptional.isEmpty()) {
            throw ResourceNotFoundException.builder()
                    .errorMessageKey("No se encontro un rol con el id proporcionado")
                    .build();
        }

        return rolOptional.get();
    }
}
