package com.gamestore.api.controller;

import com.gamestore.api.dto.DadosAutenticacao;
import com.gamestore.api.model.UserRole;
import com.gamestore.api.model.Usuario;
import com.gamestore.api.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registrar")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    // Usando injeção por construtor, exatamente como você fez nos seus outros services!
    public UsuarioController(UsuarioRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    public ResponseEntity cadastrar(@RequestBody @Valid DadosAutenticacao dados) {
        String senhaCriptografada = passwordEncoder.encode(dados.senha());

        Usuario novoUsuario = new Usuario();
        novoUsuario.setLogin(dados.login());
        novoUsuario.setSenha(senhaCriptografada);

        novoUsuario.setRole(UserRole.USER);

        repository.save(novoUsuario);

        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }
}