package org.taskmastes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.taskmastes.model.Habito;
import org.taskmastes.model.Usuario;
import org.taskmastes.service.HabitoService;
import org.taskmastes.service.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/habitos")
public class HabitoController {

    @Autowired
    private HabitoService habitoService;

    @Autowired
    private UsuarioService usuarioService;

    // Exibe todos os hábitos para o usuário autenticado
    @GetMapping
    public String listHabitos(Model model, HttpServletRequest request) {
        Long usuarioId = getUsuarioIdFromCookies(request);

        // Verificar se o ID do usuário foi encontrado
        if (usuarioId != null) {
            // Buscar os hábitos para o usuário autenticado
            List<Habito> habitosDoUsuario = habitoService.findByUsuarioId(usuarioId);
            model.addAttribute("habitos", habitosDoUsuario);
        } else {
            System.out.println("Usuário não encontrado ou cookie não disponível");
            model.addAttribute("habitos", List.of()); // Retorna uma lista vazia caso não encontre o usuário
        }

        return "habitos";
    }

    // Exibe o formulário para criar um novo hábito
    @GetMapping("/novo")
    public String showNewHabitoForm(Model model) {
        model.addAttribute("habito", new Habito());
        return "habitos/novo";
    }

    // Adiciona um novo hábito
    @PostMapping("/novo")
    public String addHabito(@RequestParam String descricao,
                            HttpServletRequest request) {
        try {
            // Pegar o ID do usuário do cookie "ID_USER"
            Long usuarioId = getUsuarioIdFromCookies(request);

            if (usuarioId == null) {
                System.out.println("ID do Usuário não encontrado no cookie");
                return "redirect:/login";
            }

            // Buscar o usuário pelo ID
            Usuario usuario = usuarioService.findById(usuarioId);
            if (usuario != null) {
                Habito habito = new Habito();
                habito.setDescricao(descricao);
                habito.setUsuario(usuario);
                habito.setDataCriacao(LocalDateTime.now());
                habitoService.save(habito);
            } else {
                System.out.println("Usuário não encontrado ou não autenticado");
            }
            return "redirect:/habitos";
        } catch (Exception e) {
            e.printStackTrace(); // Imprime o erro para verificar onde está ocorrendo o problema
            return "redirect:/error";
        }
    }

    // Excluir um hábito
    @PostMapping("/excluir/{id}")
    public String removeHabito(@PathVariable Long id) {
        habitoService.deleteById(id);
        return "redirect:/habitos";
    }

    // Helper para pegar o ID do usuário dos cookies
    private Long getUsuarioIdFromCookies(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("ID_USER".equals(cookie.getName())) {
                    try {
                        return Long.parseLong(cookie.getValue());
                    } catch (NumberFormatException e) {
                        System.out.println("Erro ao converter ID do usuário para Long: " + e.getMessage());
                    }
                }
            }
        }
        return null;
    }
}
