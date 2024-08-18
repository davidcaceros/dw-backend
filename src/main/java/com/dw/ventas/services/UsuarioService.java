package com.dw.ventas.services;

import com.dw.ventas.entities.Persona;
import com.dw.ventas.entities.Usuario;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.NuevoUsuarioRequest;
import com.dw.ventas.models.UsuarioResponse;
import com.dw.ventas.repositories.PersonaRepository;
import com.dw.ventas.repositories.RolRepository;
import com.dw.ventas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Lazy
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    private final PersonaService personaService;

    @Autowired
    public UsuarioService(final UsuarioRepository usuarioRepository,
                          final PersonaService personaService) {
        this.usuarioRepository = usuarioRepository;
        this.personaService = personaService;
    }

    @Transactional
    public void registerUser(final NuevoUsuarioRequest usuarioRequest) {
        final Persona personaSaved = personaService.registerPersona(usuarioRequest);
        final LocalDateTime now = LocalDateTime.now();

        final Usuario usuario = Usuario.builder()
                .persona(personaSaved)
                .constrasena(usuarioRequest.getContrasena())
                .estado(true)
                .fechaCreacion(now)
                .fechaActualizacion(now)
                .build();

        usuarioRepository.save(usuario);
    }

    public UsuarioResponse getUserById(final Long id) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("The user was not found.")
                        .errorMessageKey("The user was not found.")
                        .addAdditionalInformation("userId", id)
                        .build());

        return usuarioResponseBuilder(usuario);
    }

    public UsuarioResponse getUserByEmail(final String email) {
        final Usuario usuario = usuarioRepository.findByPersonaCorreo(email)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("The user was not found.")
                        .errorMessageKey("The user was not found.")
                        .addAdditionalInformation("email", email)
                        .build());

        return usuarioResponseBuilder(usuario);
    }

    public List<UsuarioResponse> getUsers() {
        final List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::usuarioResponseBuilder)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optionalUser = usuarioRepository.findByPersonaCorreo(username);

        if (optionalUser.isPresent()) {
            User.UserBuilder userBuilder = User.withUsername(username);
            Usuario usuario = optionalUser.get();
            userBuilder.password(usuario.getConstrasena()).roles(usuario.getPersona().getRol().getNombre());
            return userBuilder.build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    private UsuarioResponse usuarioResponseBuilder(final Usuario usuario) {
        return UsuarioResponse.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombre(usuario.getPersona().getPrimerNombre())
                .apellido(usuario.getPersona().getPrimerApellido())
                .correo(usuario.getPersona().getCorreo())
                .build();
    }
}
