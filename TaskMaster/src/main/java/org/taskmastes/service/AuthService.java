package org.taskmastes.service;

import org.taskmastes.model.Usuario;
import org.taskmastes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método para autenticar usuário pelo email e senha
    public boolean authenticate(String email, String rawPassword) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        // Verifica se o usuário existe e se a senha corresponde
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            System.out.println("rawPassword: " + rawPassword);
            System.out.println("usuario.getSenha(): " + usuario.getSenha());
            return BCrypt.checkpw(rawPassword, usuario.getSenha());
        }
        return false;
    }

    // Método para registrar um novo usuário
    public Usuario register(String email, String password, String nome) {
        if (usuarioRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Usuário já existe com esse email!");
        }
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        // Usando BCrypt para codificar a senha manualmente
        usuario.setSenha(BCrypt.hashpw(password, BCrypt.gensalt()));
        return usuarioRepository.save(usuario);
    }

    // Método para carregar o usuário pelo email
    public Usuario loadUserByEmail(String email) {
        // Encontre o usuário pelo email
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o email: " + email));
    }

}
