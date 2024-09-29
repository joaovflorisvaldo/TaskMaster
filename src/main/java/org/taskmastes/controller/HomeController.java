package org.taskmastes.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.taskmastes.model.Habito;
import org.taskmastes.model.Tarefa;
import org.taskmastes.model.Usuario;
import org.taskmastes.service.AuthService;
import org.taskmastes.service.HabitoService;
import org.taskmastes.service.TarefaService;

import java.util.List;

@Controller
public class HomeController {

    private final AuthService authService;
    private final TarefaService tarefaService;
    private final HabitoService habitoService;

    // Injeção dos serviços pelo construtor
    public HomeController(AuthService authService, TarefaService tarefaService, HabitoService habitoService) {
        this.authService = authService;
        this.tarefaService = tarefaService;
        this.habitoService = habitoService;
    }

    @GetMapping("/home")
    public String showHomePage(HttpServletRequest request, Model model) {
        boolean isAuthenticated = false;
        Usuario usuario = null;

        // Verificar se os cookies estão presentes
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            System.out.println("Nenhum cookie encontrado.");
            return "redirect:/login";
        }

        // Iterar sobre os cookies
        for (Cookie cookie : cookies) {
            if ("LOGIN_INFO".equals(cookie.getName()) && "true".equals(cookie.getValue())) {
                // Se encontrar o cookie, defina como autenticado
                isAuthenticated = true;

                // Agora, encontre o cookie LOGIN_USER para obter o email
                for (Cookie userCookie : cookies) {
                    if ("LOGIN_USER".equals(userCookie.getName())) {
                        String email = userCookie.getValue();
                        // Carregue o usuário pelo email armazenado no cookie LOGIN_USER
                        try {
                            usuario = authService.loadUserByEmail(email);
                        } catch (Exception e) {
                            return "redirect:/login";
                        }
                        break;
                    }
                }
                break;
            }
        }

        if (isAuthenticated && usuario != null) {
            // Adicionar informações do usuário ao modelo para a view
            model.addAttribute("nomeUsuario", usuario.getNome());

            // Buscar as últimas 10 tarefas do usuário
            List<Tarefa> tarefasRecentes = tarefaService.findRecentTasksByUsuarioId(usuario.getId());
            model.addAttribute("tarefasRecentes", tarefasRecentes);

            // Buscar os últimos 10 hábitos do usuário
            List<Habito> habitosRecentes = habitoService.findRecentHabitsByUsuarioId(usuario.getId());
            model.addAttribute("habitosRecentes", habitosRecentes);

            return "home"; // Retorna a view home.html
        } else {
            return "redirect:/login";
        }
    }
}
