package com.dw.ventas.repositories;

import com.dw.ventas.entities.Rol;
import com.dw.ventas.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByEnabledTrue();

    List<Usuario> findByRolAndEnabledTrue(Rol rol);

    List<Usuario> findByRolSlugAndEnabledTrue(String slug);
}