package org.taskmastes.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.taskmastes.model.Tarefa;
import org.taskmastes.model.Usuario;
import org.taskmastes.service.AuthService;
import org.taskmastes.service.TarefaService;
import org.taskmastes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Controller
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @Autowired
    private UsuarioService usuarioService;

    private AuthService authService;

    // Lista todas as tarefas
    @GetMapping("/tarefas")
    public String listTarefas(Model model, HttpServletRequest request) {
        Long usuarioId = null;

        // Obter o ID do usuário autenticado a partir do cookie "ID_USER"
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("ID_USER".equals(cookie.getName())) { // Alterado para buscar o cookie "ID_USER"
                    try {
                        usuarioId = Long.parseLong(cookie.getValue());
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter ID do usuário para Long: " + e.getMessage());
                    }
                    break;
                }
            }
        }

        // Verificar se o ID do usuário foi encontrado
        if (usuarioId != null) {
            // Buscar as tarefas para o usuário autenticado
            List<Tarefa> tarefasDoUsuario = tarefaService.findByUsuarioId(usuarioId);
            model.addAttribute("tarefas", tarefasDoUsuario);
        } else {
            System.out.println("Usuário não encontrado ou cookie não disponível");
            model.addAttribute("tarefas", Collections.emptyList()); // Retorna uma lista vazia caso não encontre o usuário
        }

        return "tarefas";
    }

    @PostMapping("/tarefas/nova")
    public String addTarefa(@RequestParam String titulo,
                            @RequestParam String descricao,
                            @RequestParam String dataInicio, // Agora String para converter manualmente
                            @RequestParam String dataLimite, // Agora String para converter manualmente
                            HttpServletRequest request) { // Adicionado HttpServletRequest para acessar os cookies
        try {
            // Formato de data que o campo "datetime-local" usa
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            // Convertendo a string para LocalDateTime
            LocalDateTime startDate = LocalDateTime.parse(dataInicio, formatter);
            LocalDateTime endDate = LocalDateTime.parse(dataLimite, formatter);

            // Log de verificação dos dados
            System.out.println("Descrição: " + descricao);
            System.out.println("Data de Início: " + startDate);
            System.out.println("Data Limite: " + endDate);

            // Pegar o ID do usuário do cookie "ID_USER"
            Long usuarioId = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("ID_USER".equals(cookie.getName())) { // Modificado para pegar do cookie "ID_USER"
                        try {
                            usuarioId = Long.parseLong(cookie.getValue());
                            System.out.println("ID do Usuário obtido do cookie: " + usuarioId);
                        } catch (NumberFormatException e) {
                            System.out.println("Erro ao converter ID do usuário para Long: " + e.getMessage());
                        }
                        break;
                    }
                }
            }

            // Verificar se o ID do usuário foi encontrado
            if (usuarioId == null) {
                System.out.println("ID do Usuário não encontrado no cookie");
                return "redirect:/login"; // Redireciona para a página de login caso o cookie não seja encontrado
            }

            // Buscar o usuário pelo ID
            Usuario usuario = usuarioService.findById(usuarioId);
            if (usuario != null) {
                System.out.println("Usuário encontrado: " + usuario.getNome());
                Tarefa tarefa = new Tarefa();
                tarefa.setTitulo(titulo);
                tarefa.setDescricao(descricao);
                tarefa.setDataInicio(startDate);
                tarefa.setDataLimite(endDate);
                tarefa.setConcluida(false);
                tarefa.setUsuario(usuario); // Associa a tarefa ao usuário autenticado
                tarefaService.save(tarefa);
            } else {
                System.out.println("Usuário não encontrado ou não autenticado");
            }
            return "redirect:/tarefas";
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o erro para verificar onde está ocorrendo o problema
            return "redirect:/error"; // Redireciona para uma página de erro personalizada
        }
    }

    @GetMapping("/tarefas/nova")
    public String novaTarefaForm(Model model) {
        // Adiciona um objeto Tarefa vazio ao modelo para ser usado no formulário
        model.addAttribute("tarefa", new Tarefa());
        return "tarefas/nova"; // Nome da view para adicionar nova tarefa (nova.html)
    }

    // Edita uma tarefa existente
    @PostMapping("/tarefas/editar")
    public String editTarefa(@RequestParam Long id,
                             @RequestParam String titulo,
                             @RequestParam String descricao,
                             @RequestParam String dataInicio, // Agora String para converter manualmente
                             @RequestParam String dataLimite, // Agora String para converter manualmente
                             @RequestParam Boolean concluida,
                             HttpServletRequest request) {
        try {
            // Formato de data que o campo "datetime-local" usa
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

            // Convertendo a string para LocalDateTime
            LocalDateTime startDate = LocalDateTime.parse(dataInicio, formatter);
            LocalDateTime endDate = LocalDateTime.parse(dataLimite, formatter);

            // Buscar a tarefa pelo ID
            Tarefa tarefa = tarefaService.findById(id);

            // Obter o ID do usuário autenticado do cookie "ID_USER"
            Long usuarioId = null;
            if (request.getCookies() != null) {
                for (Cookie cookie : request.getCookies()) {
                    if ("ID_USER".equals(cookie.getName())) {
                        try {
                            usuarioId = Long.parseLong(cookie.getValue());
                        } catch (NumberFormatException e) {
                            System.out.println("Erro ao converter ID do usuário para Long: " + e.getMessage());
                        }
                        break;
                    }
                }
            }

            // Verificar se a tarefa e o usuário existem
            if (tarefa != null && usuarioId != null) {
                // Buscar o usuário pelo ID
                Usuario usuario = usuarioService.findById(usuarioId);

                if (usuario != null) {
                    // Atualizar os dados da tarefa
                    tarefa.setTitulo(titulo);
                    tarefa.setDescricao(descricao);
                    tarefa.setDataInicio(startDate);
                    tarefa.setDataLimite(endDate);
                    tarefa.setConcluida(concluida);
                    tarefa.setUsuario(usuario);

                    // Salvar a tarefa atualizada
                    tarefaService.save(tarefa);
                    System.out.println("Tarefa atualizada com sucesso.");
                } else {
                    System.out.println("Usuário não encontrado com o ID do cookie");
                }
            } else {
                System.out.println("Tarefa ou usuário não encontrados");
            }
            return "redirect:/tarefas";
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o erro para verificar onde está ocorrendo o problema
            return "redirect:/error"; // Redireciona para uma página de erro personalizada
        }
    }

    @GetMapping("/tarefas/editar/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        // Buscar a tarefa pelo ID
        Tarefa tarefa = tarefaService.findById(id);
        if (tarefa != null) {
            model.addAttribute("tarefa", tarefa);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String formattedDataInicio = tarefa.getDataInicio().format(formatter);
            String formattedDataLimite = tarefa.getDataLimite().format(formatter);
            model.addAttribute("formattedDataInicio", formattedDataInicio);
            model.addAttribute("formattedDataLimite", formattedDataLimite);
            return "tarefas/editar"; // Nome do template Thymeleaf para edição
        }
        return "redirect:/tarefas"; // Redirecionar caso a tarefa não seja encontrada
    }

    @PostMapping("/tarefas/concluir/{id}")
    public String concluirTarefa(@PathVariable Long id) {
        // Buscar a tarefa pelo ID
        Tarefa tarefa = tarefaService.findById(id);

        // Verificar se a tarefa existe
        if (tarefa != null) {
            // Atualizar o status para concluída
            tarefa.setConcluida(true);

            // Salvar a tarefa atualizada
            tarefaService.save(tarefa);
            System.out.println("Tarefa concluída com sucesso.");
        } else {
            System.out.println("Tarefa não encontrada com o ID: " + id);
        }

        // Redirecionar para a lista de tarefas após a conclusão
        return "redirect:/tarefas";
    }

    // Remove uma tarefa pelo ID
    @GetMapping("/tarefas/excluir/{id}")
    public String removeTarefa(@PathVariable Long id) {
        // Exclui a tarefa pelo ID fornecido na URL
        tarefaService.deleteById(id);
        return "redirect:/tarefas";
    }

}
