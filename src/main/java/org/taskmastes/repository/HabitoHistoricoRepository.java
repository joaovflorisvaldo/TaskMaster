package org.taskmastes.repository;

import org.taskmastes.model.HabitoHistorico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitoHistoricoRepository extends JpaRepository<HabitoHistorico, Long> {
}