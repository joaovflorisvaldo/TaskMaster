package org.taskmastes.service;

import org.taskmastes.model.HabitoHistorico;
import org.taskmastes.repository.HabitoHistoricoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitoHistoricoService {

    @Autowired
    private HabitoHistoricoRepository habitoHistoricoRepository;

    public List<HabitoHistorico> findAll() {
        return habitoHistoricoRepository.findAll();
    }

    public HabitoHistorico findById(Long id) {
        Optional<HabitoHistorico> historico = habitoHistoricoRepository.findById(id);
        return historico.orElse(null);
    }

    public HabitoHistorico save(HabitoHistorico habitoHistorico) {
        return habitoHistoricoRepository.save(habitoHistorico);
    }

    public void deleteById(Long id) {
        habitoHistoricoRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return habitoHistoricoRepository.existsById(id);
    }
}