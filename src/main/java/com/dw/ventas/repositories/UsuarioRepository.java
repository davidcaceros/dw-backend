package com.dw.ventas.repositories;

import com.dw.ventas.entities.Rol;
import com.dw.ventas.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByPersonaCorreo(String correo);
}