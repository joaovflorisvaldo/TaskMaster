package org.taskmastes.controller;

import org.taskmastes.model.Usuario;
import org.taskmastes.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public String listUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.findAll());
        return "usuarios/list";
    }

    @PostMapping("/usuarios/add")
    public String addUsuario(@RequestParam String nome, @RequestParam String email, @RequestParam String senha) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuarioService.save(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/edit")
    public String editUsuario(@RequestParam Long id, @RequestParam String nome,
                              @RequestParam String email, @RequestParam String senha) {
        Usuario usuario = usuarioService.findById(id);
        if (usuario != null) {
            usuario.setNome(nome);
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuarioService.edit(id, usuario);
        }
        return "redirect:/usuarios";
    }

    @PostMapping("/usuarios/remove")
    public String removeUsuario(@RequestParam Long id) {
        usuarioService.deleteById(id);
        return "redirect:/usuarios";
    }
}