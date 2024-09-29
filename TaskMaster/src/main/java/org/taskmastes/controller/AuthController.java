package org.taskmastes.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.taskmastes.model.Usuario;
import org.taskmastes.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;
import org.slf4j.Logger;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.IOException;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    // Exibe a página de login
    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login"; // Nome da view (login.html)
    }

    // Processa o formulário de login
    @PostMapping("/processLogin")
    public void processLogin(
            @Valid @ModelAttribute("loginForm") LoginForm loginForm,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response,
            Model model) throws IOException {

        // Verifica se há erros de validação
        if (bindingResult.hasErrors()) {
            response.sendRedirect("/login?error=true");
            return;
        }

        boolean authenticated = authService.authenticate(loginForm.getEmail(), loginForm.getPassword());

        if (authenticated) {
            System.out.println("Usuário autenticado com sucesso!");

            // Carregar detalhes do usuário autenticado
            Usuario usuario = authService.loadUserByEmail(loginForm.getEmail());

            // Adicionar cookie LOGIN_USER
            Cookie usernameCookie = new Cookie("LOGIN_USER", usuario.getEmail());
            usernameCookie.setHttpOnly(true);
            usernameCookie.setSecure(false); // Defina como true em ambiente de produção para HTTPS
            usernameCookie.setPath("/");
            response.addCookie(usernameCookie);

            // Adicionar cookie LOGIN_INFO (indicador de autenticação)
            Cookie authCookie = new Cookie("LOGIN_INFO", "true");
            authCookie.setHttpOnly(true);
            authCookie.setSecure(false); // Defina como true em ambiente de produção para HTTPS
            authCookie.setPath("/");
            authCookie.setMaxAge(3600); // Expira em 1 hora
            response.addCookie(authCookie);

            // Adicionar cookie LOGIN_INFO (indicador de autenticação)
            Cookie idCookie = new Cookie("ID_USER", String.valueOf(usuario.getId()));
            authCookie.setHttpOnly(true);
            authCookie.setSecure(false); // Defina como true em ambiente de produção para HTTPS
            authCookie.setPath("/");
            authCookie.setMaxAge(3600); // Expira em 1 hora
            response.addCookie(idCookie);

            // Adicionar cookie LOGIN_INFO (indicador de autenticação)
            Cookie nameCookie = new Cookie("NOME_USER", usuario.getNome());
            authCookie.setHttpOnly(true);
            authCookie.setSecure(false); // Defina como true em ambiente de produção para HTTPS
            authCookie.setPath("/");
            authCookie.setMaxAge(3600); // Expira em 1 hora
            response.addCookie(nameCookie);


            // Redirecionar para `/home` após login bem-sucedido
            response.sendRedirect("/home");
        } else {
            logger.info("Autenticação falhou - adicionando mensagem de erro");
            response.sendRedirect("/login?error=true");
        }
    }

    // Exibe a página de cadastro
    @GetMapping("/cadastro")
    public String showSignupPage(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "cadastro"; // Nome da view para cadastro (cadastro.html)
    }

    // Processa o formulário de cadastro
    @PostMapping("/cadastro")
    public String processSignup(
            @Valid @ModelAttribute("signupForm") SignupForm signupForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        // Verifica se há erros de validação
        if (bindingResult.hasErrors()) {
            return "cadastro"; // Retorna a view de cadastro com os erros
        }

        // Registra o usuário
        try {
            authService.register(signupForm.getEmail(), signupForm.getPassword(), signupForm.getNome());
            redirectAttributes.addFlashAttribute("success", "Cadastro realizado com sucesso!");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/cadastro";
        }
    }

    // Classe interna para validação dos dados do formulário de login
    public static class LoginForm {
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Por favor, insira um email válido")
        private String email;

        @NotBlank(message = "A senha é obrigatória")
        private String password;

        // Getters e Setters
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    // Classe interna para validação dos dados do formulário de cadastro
    public static class SignupForm {
        @NotBlank(message = "O nome é obrigatório")
        private String nome;

        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Por favor, insira um email válido")
        private String email;

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter pelo menos 6 caracteres")
        private String password;

        // Getters e Setters
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
