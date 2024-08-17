package com.dw.ventas.services;

import com.dw.ventas.entities.Rol;
import com.dw.ventas.entities.Usuario;
import com.dw.ventas.exception.impl.ResourceNotFoundException;
import com.dw.ventas.models.UsuarioDTO;
import com.dw.ventas.models.UsuarioRequest;
import com.dw.ventas.models.UsuarioResponse;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Service
@Lazy
public class UsuarioService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository,
                          RolRepository rolRepository,
                          @Lazy PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            return null;
        }

        Rol rol = rolRepository.getReferenceById(usuarioDTO.getRolId());
        final LocalDateTime now = LocalDateTime.now();
        Usuario usuario = Usuario.builder()
                .email(usuarioDTO.getEmail())
                .contraseña(passwordEncoder.encode(usuarioDTO.getContrasena()))
                .nombre(usuarioDTO.getNombre())
                .apellido(usuarioDTO.getApellido())
                .dpi(usuarioDTO.getDpi())
                .telefono(usuarioDTO.getTelefono())
                .rol(rol)
                .fechaCreacion(now)
                .fechaActualizacion(null)
                .enabled(true)
                .build();

        return usuarioRepository.save(usuario);
    }

    public List<UsuarioResponse> getUsers(final String slug) {
        final List<Usuario> usuarios;
        if (isNotBlank(slug)) {
            usuarios = usuarioRepository.findByRolSlugAndEnabledTrue(slug);
        } else {
            usuarios = usuarioRepository.findByEnabledTrue();
        }

        return usuarios.stream()
                .map(usuario ->
                        UsuarioResponse.builder()
                                .id(usuario.getId())
                                .nombre(usuario.getNombre())
                                .apellido(usuario.getApellido())
                                .email(usuario.getEmail())
                                .dpi(usuario.getDpi())
                                .rolId(usuario.getRol().getId())
                                .rolSlug(usuario.getRol().getSlug())
                                .telefono(usuario.getTelefono())
                                .enabled(usuario.isEnabled())
                                .build())
                .collect(Collectors.toList());
    }

    public UsuarioResponse getUserById(final Long id) {
        final Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("The user was not found.")
                        .errorMessageKey("The user was not found.")
                        .addAdditionalInformation("userId", id)
                        .build());

        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .dpi(usuario.getDpi())
                .rolId(usuario.getRol().getId())
                .rolSlug(usuario.getRol().getSlug())
                .telefono(usuario.getTelefono())
                .enabled(usuario.isEnabled())
                .build();
    }

    public UsuarioResponse getUserByEmail(final String email) {
        final Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .message("The user was not found.")
                        .errorMessageKey("The user was not found.")
                        .addAdditionalInformation("email", email)
                        .build());

        return UsuarioResponse.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .apellido(usuario.getApellido())
                .email(usuario.getEmail())
                .dpi(usuario.getDpi())
                .rolId(usuario.getRol().getId())
                .rolSlug(usuario.getRol().getSlug())
                .telefono(usuario.getTelefono())
                .enabled(usuario.isEnabled())
                .build();
    }

    public UsuarioResponse updateUser(final UsuarioRequest usuarioRequest) {
        final Usuario usuario = usuarioRepository.findById(usuarioRequest.getId())
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .errorMessageKey("The user was not found.")
                        .build());


        final Rol rol = rolRepository.findById(usuarioRequest.getRolId())
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .errorMessageKey("The rol was not found.")
                        .build());

        usuario.setNombre(usuarioRequest.getNombre());
        usuario.setApellido(usuarioRequest.getApellido());
        usuario.setRol(rol);
        usuario.setTelefono(usuarioRequest.getTelefono());
        usuario.setEnabled(usuarioRequest.getEnabled());

        final Usuario usuarioActualizado = usuarioRepository.save(usuario);

        return UsuarioResponse.builder()
                .id(usuarioActualizado.getId())
                .nombre(usuarioActualizado.getNombre())
                .apellido(usuarioActualizado.getApellido())
                .email(usuarioActualizado.getEmail())
                .dpi(usuarioActualizado.getDpi())
                .rolId(usuarioActualizado.getRol().getId())
                .rolSlug(usuarioActualizado.getRol().getSlug())
                .telefono(usuarioActualizado.getTelefono())
                .enabled(usuarioActualizado.isEnabled())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> optionalUser = usuarioRepository.findByEmail(username);

        if (optionalUser.isPresent()) {
            User.UserBuilder userBuilder = User.withUsername(username);
            Usuario usuario = optionalUser.get();
            userBuilder.password(usuario.getContraseña()).roles(usuario.getRol().getNombre());
            return userBuilder.build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

}
