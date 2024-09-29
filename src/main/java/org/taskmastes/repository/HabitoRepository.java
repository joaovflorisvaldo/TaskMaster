package org.taskmastes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.taskmastes.model.Habito;

import java.util.List;

public interface HabitoRepository extends JpaRepository<Habito, Long> {
    List<Habito> findByUsuarioId(Long usuarioId);

}
