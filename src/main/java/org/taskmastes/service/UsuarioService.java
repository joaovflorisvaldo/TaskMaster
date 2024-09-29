package org.taskmastes.service;

import org.taskmastes.model.Usuario;
import org.taskmastes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Encontra todos os usuários
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Encontra um usuário pelo ID
    public Usuario findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    // Salva um novo usuário ou atualiza um existente
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Atualiza um usuário existente pelo ID
    public Usuario edit(Long id, Usuario updatedUsuario) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(updatedUsuario.getNome());
            usuario.setEmail(updatedUsuario.getEmail());
            usuario.setSenha(updatedUsuario.getSenha());
            // Adicione outros campos que deseja atualizar
            return usuarioRepository.save(usuario);
        }).orElse(null);
    }

    // Exclui um usuário pelo ID
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Encontra um usuário pelo email
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado com o email: " + email));
    }
}
