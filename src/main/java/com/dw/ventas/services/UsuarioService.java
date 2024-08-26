package com.dw.ventas.services;

import com.dw.ventas.entities.Persona;
import com.dw.ventas.entities.Usuario;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.RegisterRequest;
import com.dw.ventas.models.RegisterResponse;
import com.dw.ventas.models.UsuarioResponse;
import com.dw.ventas.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    public RegisterResponse registerUser(final RegisterRequest usuarioRequest, String encode) {

        final Persona personaSaved = personaService.registerPersonaForUser(usuarioRequest);

        final LocalDateTime now = LocalDateTime.now();

        final Usuario usuario = Usuario.builder()
                .persona(personaSaved)
                .contrasena(encode)
                .activo(true)
                .fechaCreacion(now)
                .fechaActualizacion(now)
                .build();

        final Usuario usuarioSaved = usuarioRepository.save(usuario);

        return RegisterResponse.builder()
                .idUsuario(usuarioSaved.getIdUsuario())
                .usuario(usuarioSaved.getPersona().getCorreo())
                .build();

    }

    public UsuarioResponse findUserById(final Integer id) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("The user was not found.")
                        .errorMessageKey("The user was not found.")
                        .addAdditionalInformation("userId", id)
                        .build());

        return usuarioResponseBuilder(usuario);
    }

    public UsuarioResponse findUserByEmail(final String email) {
        final Usuario usuario = usuarioRepository.findByPersonaCorreo(email)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("The user was not found.")
                        .errorMessageKey("The user was not found.")
                        .addAdditionalInformation("email", email)
                        .build());

        return usuarioResponseBuilder(usuario);
    }

    public List<UsuarioResponse> findAllUsers() {
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
            userBuilder.password(usuario.getContrasena()).roles(usuario.getPersona().getRol().getNombre());
            return userBuilder.build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public UsuarioResponse usuarioResponseBuilder(final Usuario usuario) {
        return UsuarioResponse.builder()
                .idUsuario(usuario.getIdUsuario())
                .activo(usuario.getActivo())
                .fechaCreacion(usuario.getFechaCreacion().toString())
                .fechaActualizacion(usuario.getFechaActualizacion().toString())
                .persona(usuario.getPersona())
                .build();
    }
}
