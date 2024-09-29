package org.taskmastes.service;

import org.taskmastes.model.Tarefa;
import org.taskmastes.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository tarefaRepository;

    public List<Tarefa> findAll() {
        return tarefaRepository.findAll();
    }

    public Tarefa findById(Long id) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(id);
        return tarefa.orElse(null);
    }

    public Tarefa save(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public void deleteById(Long id) {
        tarefaRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return tarefaRepository.existsById(id);
    }

    public List<Tarefa> findRecentTasksByUsuario(Long userId) {
        return tarefaRepository.findTop10ByUsuarioIdOrderByDataCriacaoDesc(userId);
    }

    public List<Tarefa> findByUsuarioId(Long usuarioId) {
        return tarefaRepository.findByUsuarioId(usuarioId);
    }
    public List<Tarefa> findRecentTasksByUsuarioId(Long usuarioId) {
        // Busca todas as tarefas do usuário e limita a 10 registros
        return tarefaRepository.findByUsuarioId(usuarioId)
                .stream()
                .sorted((t1, t2) -> t2.getDataLimite().compareTo(t1.getDataLimite())) // Ordena por data de limite (ou outro campo desejado)
                .limit(10) // Limita a 10 registros
                .collect(Collectors.toList());
    }
}