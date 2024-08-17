package com.dw.ventas.repositories;

import com.dw.ventas.entities.SupervisorTecnico;
import com.dw.ventas.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupervisorTecnicoRepository extends JpaRepository<SupervisorTecnico, Long> {
    Optional<SupervisorTecnico> findByTecnico(Usuario usuario);
}
