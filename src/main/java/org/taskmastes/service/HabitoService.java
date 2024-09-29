package org.taskmastes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taskmastes.model.Habito;
import org.taskmastes.repository.HabitoRepository;
import java.util.Optional;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class HabitoService {

    @Autowired
    private HabitoRepository habitoRepository;

    public List<Habito> findAll() {
        return habitoRepository.findAll();
    }

    public List<Habito> findByUsuarioId(Long usuarioId) {
        return habitoRepository.findByUsuarioId(usuarioId);
    }

    public Habito save(Habito habito) {
        return habitoRepository.save(habito);
    }

    public void deleteById(Long id) {
        habitoRepository.deleteById(id);
    }

    public Habito findById(Long habitoId) {
        Optional<Habito> habito = habitoRepository.findById(habitoId);
        return habito.orElse(null);
    }
    public List<Habito> findRecentHabitsByUsuarioId(Long usuarioId) {
        // Busca todos os hábitos do usuário e limita a 10 registros
        return habitoRepository.findByUsuarioId(usuarioId)
                .stream()
                .sorted((h1, h2) -> h2.getDataCriacao().compareTo(h1.getDataCriacao())) // Ordena por data de criação (ou outro campo desejado)
                .limit(10) // Limita a 10 registros
                .collect(Collectors.toList());
    }
}
